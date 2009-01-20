import grails.util.GrailsUtil
import org.codehaus.groovy.grails.plugins.starksecurity.*
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch
import org.springframework.security.providers.ProviderManager
import org.springframework.security.providers.ldap.LdapAuthenticationProvider
import org.springframework.security.providers.ldap.authenticator.BindAuthenticator
import org.springframework.security.ldap.populator.DefaultLdapAuthoritiesPopulator
import org.springframework.security.userdetails.UserDetailsService
import org.springframework.security.ldap.DefaultSpringSecurityContextSource
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.*
import org.springframework.security.userdetails.ldap.LdapUserDetailsImpl

class StarkSecurityGrailsPlugin {
    def version = "0.4.1"
    def dependsOn = [:]

    static log = LogFactory.getLog("org.codehaus.groovy.grails.plugins.starksecurity.StarkSecurityGrailsPlugin")
   
    def author = "Ola Bildtsen"
    def authorEmail = "info@bildtsen.com"
    def title = "A robust, convention-based implementation of Spring Security for Grails"
    def description = '''\
The Stark Security plugin -- 'stark' as in simple, but also 'strong' in Swedish -- is an implementation of
Spring Security for Grails.  It employs a lock-down approach to security, meaning all access is
disallowed by default.  Developers open access for roles on a controller-method basis, and non-controller
URLs (e.g. /js/**) can be configured separately.  All mappings are read by convention,
there is no configuration beyond hooking up the desired authentication implementations.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/Stark+Security+Plugin"
    
    def watchedResources = ["file:./grails-app/controllers/*Controller.groovy",
                            "file:./grails-app/conf/StarkSecurityConfig.groovy"]

    def mergeConfig(starkSecurityConfigClass = null) {
        def theClass
        if (ConfigurationHolder.config.starksecurity) {
            // Need to wipe out any existing props, otherwise they appear to be appended...?
            ConfigurationHolder.config.starksecurity = null
        }
        if (starkSecurityConfigClass) {
        	theClass = starkSecurityConfigClass
        } else {
        	GroovyClassLoader classLoader = new GroovyClassLoader(getClass().classLoader)
            try {
            	theClass = classLoader.loadClass('StarkSecurityConfig')
            } catch (ClassNotFoundException ex) {
                log.warn "StarkSecurityConfig.groovy not found.  No authentication managers will be created."
            }
        }
        if (theClass) {
            ConfigurationHolder.config.merge(new ConfigSlurper(GrailsUtil.environment).parse(theClass))
        }
    }

    def doWithSpring = {
        // This sections sets up most of the filter beans we'll need.

        mergeConfig()

        def starkConfig = ConfigurationHolder.config.starksecurity
        
        httpSessionContextIntegrationFilter(org.springframework.security.context.HttpSessionContextIntegrationFilter){}

        def list = new org.springframework.security.ui.logout.LogoutHandler[1]
        list[0]=new org.springframework.security.ui.logout.SecurityContextLogoutHandler()
        logoutFilter(org.springframework.security.ui.logout.LogoutFilter, starkConfig ? starkConfig.logoutUrl : '/', list){}

        securityContextHolderAwareRequestFilter(org.springframework.security.wrapper.SecurityContextHolderAwareRequestFilter){}

        anonymousProcessingFilter(org.springframework.security.providers.anonymous.AnonymousProcessingFilter) {
            key = "foo"
            userAttribute = "anonymousUser,ROLE_ANONYMOUS"
            removeAfterRequest = false
        }

        exceptionTranslationFilter(org.springframework.security.ui.ExceptionTranslationFilter){
            authenticationEntryPoint = ref("authenticationEntryPoint")
        }

        authenticationEntryPoint(org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint){
            loginFormUrl = starkConfig ? starkConfig.loginFormUrl : '/'
            forceHttps = "false"
        }
        
//        pathFilterInvocationDefinitionMap(PathFilterInvocationDefinitionMap) {
//            urlMappingsHolder = ref("grailsUrlMappingsHolder")
//        }

        filterInvocationInterceptor(org.springframework.security.intercept.web.FilterSecurityInterceptor){
            authenticationManager = ref("authenticationManagerAnonymous")
            accessDecisionManager = ref("accessDecisionManager")
//            objectDefinitionSource = ref("pathFilterInvocationDefinitionMap")
            objectDefinitionSource = new PathFilterInvocationDefinitionMap()
        }

        authenticationManagerAnonymous(ProviderManager) {
            providers = [ref("anonymousAuthenticationProvider")]
        }

        anonymousAuthenticationProvider(org.springframework.security.providers.anonymous.AnonymousAuthenticationProvider) {
            key = "foo"
        }

        accessDecisionManager(org.springframework.security.vote.AffirmativeBased){
            allowIfAllAbstainDecisions = "false"
            decisionVoters=[
                ref("roleVoter"),
                ref("authenticatedVoter")]
        }
        roleVoter(org.springframework.security.vote.RoleVoter){}
        authenticatedVoter(org.springframework.security.vote.AuthenticatedVoter){}

        loggerListener(org.springframework.security.event.authentication.LoggerListener){}

        // Add a SessionExpirationFilter, which will redirect any expired session to a particular URL
        // Setting this property to the empty string (or removing it altogether) disables this filter
        // i.e. no redirect when detecting expired session.
        sessionExpirationFilter(org.codehaus.groovy.grails.plugins.starksecurity.SessionExpirationFilter) {
            expiredRedirectUrl = starkConfig?.sessionExpiredUrl ? starkConfig.sessionExpiredUrl : null
        }

        starkSecurityEventListener(StarkSecurityEventListener) {}

        beforeAuthenticationFilter(UtilityFilter, starkConfig?.onBeforeAuthentication ? starkConfig?.onBeforeAuthentication : null) {}
        beforeAuthorizationFilter(UtilityFilter, starkConfig?.onBeforeAuthorization ? starkConfig?.onBeforeAuthorization : null) {}
        afterAuthorizationFilter(UtilityFilter, starkConfig?.onAfterAuthorization ? starkConfig?.onAfterAuthorization : null) {}

    }

    def doWithDynamicMethods = { applicationContext ->

        // We need to look up the actual mappings during this phase, as we need access to the finished 
        // grails objects for controllers and UrlMappings
        def filterInvocationInterceptor = applicationContext.getBean('filterInvocationInterceptor')
        def pathFilterMap = filterInvocationInterceptor.objectDefinitionSource
        pathFilterMap.urlMappingsHolder = applicationContext.getBean('grailsUrlMappingsHolder')
//    	def pathFilterMap = applicationContext.getBean('pathFilterInvocationDefinitionMap')

    	// Establish one-off authentications, as provided in in the custom config props
        setAuthorizationsByConfig(pathFilterMap)

    	// Establish controller-method-level authentications
        application.getArtefacts(ControllerArtefactHandler.TYPE).each { controller ->
            setAuthorizationsByController(pathFilterMap, controller)
        }
    }

    private setAuthorizationsByController(pathFilterMap, controller) {
        log.debug("Setting auths for controller: ${controller.fullName}, ${controller.packageName}")
        def auths = controller.getPropertyValue('authorizations')
	    // If controller defines auths, remove all existing paths for this controller (passing in e.g. /admin/**).
	    if (auths) {
		    pathFilterMap.removeControllerPaths("/${controller.logicalPropertyName}/**")
	    }

	    // Find all URIs for the controller that end in **
        controller.URIs.grep(~/.*\*\*$/)?.sort().each { uri ->
	        // Extract the method name from the URI (e.g. 'index' from /admin/index**)
			(uri =~ /(\w*)\/\*\*$/).each { all, methodName ->
				if (auths?."${methodName}") {
					// Add the method path with authorization roles taken from controller map
					pathFilterMap.putControllerPath(uri, auths."${methodName}")
				} else if (!pathFilterMap.isMapped(uri)) {
				    log.debug("No authorization mapping for uri: ${uri}")
				}
			}
		}
	}

	private setAuthorizationsByConfig(pathFilterMap) {

        pathFilterMap.removeConfigPaths("/**")

        if (ConfigurationHolder.config.starksecurity) {
            def auths = ConfigurationHolder.config.starksecurity.authorizations
            auths?.each { entry ->
                log.debug("Adding config path: ${entry.key}: ${entry.value}")
                pathFilterMap.putConfigPath(entry.key, entry.value)
            }
        }
	}
	
    /** This is where we set up the authentication filters, because we need access to the environment-specific
      * properties.
      */
    def doWithApplicationContext = { applicationContext ->

        def authFilters = []

        ConfigurationHolder.config.starksecurity?.authenticationManagers?.each { authManager ->
        
        	def authProviders = []
        	def i = 1
        	authManager.authenticationProviders?.each { authProvider ->
        		if (authProvider['userDetailsService']) {
        			authProviders += createDaoAuthenticationProvider(applicationContext, authProvider)
        		} else {
        			authProviders += createLdapAuthenticationProvider(applicationContext, authProvider, authManager.name + i)
                    i++
        		}
        	}
        	
        	def bb = new grails.spring.BeanBuilder(applicationContext)
	
	        bb.beans {

        		"authenticationManager${authManager.name}"(ProviderManager) {
	                providers = authProviders.collect { ref(it) }
	            }
	
	            "authenticationProcessingFilter${authManager.name}"(org.springframework.security.ui.webapp.AuthenticationProcessingFilter) {
	                authenticationManager = ref("authenticationManager${authManager.name}")
	                authenticationFailureUrl = authManager.authenticationFailureUrl
	                defaultTargetUrl = authManager.authenticationSuccessUrl
	                alwaysUseDefaultTargetUrl = authManager.alwaysUseDefaultTargetUrl
	                filterProcessesUrl = authManager.processesUrl
	            }
	        }
	        
            bb.registerBeans(applicationContext)

            log.debug "Created authentication manager ${authManager.name}"
            
            authFilters += "authenticationProcessingFilter${authManager.name}"
        }

        if (authFilters.size < 1) {
            log.warn "No authentication managers are defined."
        }

        // The ordering of these filters matters, hence the funky set concatenation.
        def filters =
            ["sessionExpirationFilter",
             "httpSessionContextIntegrationFilter",
             "logoutFilter",
             "beforeAuthenticationFilter"] +
            authFilters +
            ["securityContextHolderAwareRequestFilter",
             "anonymousProcessingFilter",
             "exceptionTranslationFilter",
             "beforeAuthorizationFilter",
             "filterInvocationInterceptor",
             "afterAuthorizationFilter"]

        def bb = new grails.spring.BeanBuilder(applicationContext)
        bb.beans {
            starkSecurityFilterChain(org.springframework.security.util.FilterChainProxy) {
                filterInvocationDefinitionSource="""
                    CONVERT_URL_TO_LOWERCASE_BEFORE_COMPARISON
                    PATTERN_TYPE_APACHE_ANT
                    /**=${filters.join(',')}
                    """
            }
        }
        bb.registerBeans(applicationContext)
	}

    private createDaoAuthenticationProvider(applicationContext, daoService) {
        def bb = new grails.spring.BeanBuilder(applicationContext)

        bb.beans {
            "passwordEncoder${daoService.userDetailsService}"(org.springframework.security.providers.encoding.MessageDigestPasswordEncoder, daoService.passwordEncoding.algorithm) {
                encodeHashAsBase64 = daoService.passwordEncoding.encodeBase64
            }

            "authenticationProvider${daoService.userDetailsService}"(org.springframework.security.providers.dao.DaoAuthenticationProvider) {
                userDetailsService = ref(daoService.userDetailsService)
                passwordEncoder = ref("passwordEncoder${daoService.userDetailsService}")
            }
        }
        
        log.debug "Created DAO authentication provider ${daoService.userDetailsService}"

        bb.registerBeans(applicationContext)

        return "authenticationProvider${daoService.userDetailsService}"
    }

    private createLdapAuthenticationProvider(applicationContext, ldapServiceProps, ldapProviderName) {
        def bb = new grails.spring.BeanBuilder(applicationContext)

        bb.beans {

            "ldapContextSource${ldapProviderName}"(DefaultSpringSecurityContextSource, ldapServiceProps.url) {
                userDn = ldapServiceProps.managerDn
                password = ldapServiceProps.managerPassword
                baseEnvironmentProperties = [
                        "com.sun.jndi.ldap.connect.timeout": "20000",
                        "com.sun.jndi.ldap.connect.pool.timeout": "20000",
                        "java.naming.referral": "follow"]
                pooled = false
            }

            "ldapUserSearch${ldapProviderName}"(FilterBasedLdapUserSearch, ldapServiceProps.userSearchBase, "(sAMAccountName={0})", ref("ldapContextSource${ldapProviderName}")) {
                searchSubtree = true
                searchTimeLimit = 5000
            }

            "ldapAuthenticator${ldapProviderName}"(BindAuthenticator, ref("ldapContextSource${ldapProviderName}")) {
                userSearch = ref("ldapUserSearch${ldapProviderName}")
            }

            "ldapAuthoritiesPopulator${ldapProviderName}"(DefaultLdapAuthoritiesPopulator, ref("ldapContextSource${ldapProviderName}"), ldapServiceProps.groupsPath) {
                groupRoleAttribute="cn"
            }

            "ldapAuthenticationProvider${ldapProviderName}"(LdapAuthenticationProvider, ref("ldapAuthenticator${ldapProviderName}"), ref("ldapAuthoritiesPopulator${ldapProviderName}")) {}

        }

        bb.registerBeans(applicationContext)

        return "ldapAuthenticationProvider${ldapProviderName}"
    }
    
    /** Called when a controller or StarkSecurityConfig is changed in development.
     */
    def onChange = { event ->

        if (event.source && event.ctx && event.application) {
            def filterInvocationInterceptor = event.ctx.getBean('filterInvocationInterceptor')
            def pathFilterMap = filterInvocationInterceptor.objectDefinitionSource
            if (event.source.name == 'StarkSecurityConfig') {
                log.debug("Detected change to StarkSecurityConfig.groovy")
                mergeConfig(event.source)
                setAuthorizationsByConfig(pathFilterMap)
                def starkConfig = ConfigurationHolder.config.starksecurity
                def utilityFilter = event.ctx.getBean('beforeAuthenticationFilter')
                utilityFilter.handler = starkConfig?.onBeforeAuthentication ? starkConfig?.onBeforeAuthentication : null
                utilityFilter = event.ctx.getBean('beforeAuthorizationFilter')
                utilityFilter.handler = starkConfig?.onBeforeAuthorization ? starkConfig?.onBeforeAuthorization : null
                utilityFilter = event.ctx.getBean('afterAuthorizationFilter')
                utilityFilter.handler = starkConfig?.onAfterAuthorization ? starkConfig?.onAfterAuthorization : null
            } else if (application.isControllerClass(event.source)) {
                def controllerClass = application.getControllerClass(event.source?.name)
            	log.debug("Detected change to controller ${event.source.name}")
            	setAuthorizationsByController(pathFilterMap, controllerClass)
            }
        }
    }

    def doWithWebDescriptor = {webXml ->
    
        def contextParam = webXml."context-param"
        contextParam[contextParam.size()-1]+{
            'filter' {
                'filter-name'('starkSecurityFilterChain')
                'filter-class'('org.springframework.web.filter.DelegatingFilterProxy')
                'init-param'{
                    'param-name'('targetClass')
                    'param-value'('org.springframework.security.util.FilterChainProxy')
                }
            }
        }

        def filters = webXml.'filter'
        def lastFilter = filters[filters.size() - 1]
        lastFilter + {
            'filter-mapping'{
                'filter-name'('starkSecurityFilterChain')
                'url-pattern'("/*")
            }
        }
    }
}
