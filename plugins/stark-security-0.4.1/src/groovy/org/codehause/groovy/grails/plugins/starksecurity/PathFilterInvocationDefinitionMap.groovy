package org.codehaus.groovy.grails.plugins.starksecurity

import org.springframework.security.ConfigAttributeDefinition
import org.codehaus.groovy.grails.commons.ControllerArtefactHandler
import org.springframework.security.intercept.web.*
import org.springframework.security.SecurityConfig
import org.springframework.util.AntPathMatcher
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.web.mapping.UrlMappingsHolder
import org.codehaus.groovy.grails.web.util.WebUtils
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest

class PathFilterInvocationDefinitionMap implements FilterInvocationDefinitionSource {
    static log = LogFactory.getLog("org.codehaus.groovy.grails.plugins.starksecurity.PathFilterInvocationDefinitionMap")
    def controllerPathMap = new HashMap()  // Holds contoller-method authorizations
    def configPathMap = new HashMap() // Holds config authorizations, those for non-controller URLs
    def pathMatcher = new AntPathMatcher()
    UrlMappingsHolder urlMappingsHolder
    
    public boolean supports(Class clazz) {
    	return clazz == FilterInvocation
    }
    
    public ConfigAttributeDefinition getAttributes(Object filterInvocation) {
    	log.debug "Incoming filterInvocation.requestUrl: ${filterInvocation.requestUrl}"
    	log.debug "Incoming filterInvocation.fullRequestUrl: ${filterInvocation.fullRequestUrl}"
    	log.debug "Incoming httpRequest.requestURI: ${filterInvocation.httpRequest.requestURI}"
    	log.debug "Incoming httpRequest.requestURL: ${filterInvocation.httpRequest.requestURL}"
    	log.debug "Incoming httpRequest.servletPath: ${filterInvocation.httpRequest.servletPath}"
    	log.debug "Incoming httpRequest.pathTranslated: ${filterInvocation.httpRequest.pathTranslated}"
    	log.debug "Incoming httpRequest.contextPath: ${filterInvocation.httpRequest.contextPath}"
    	
    	def requestedUrl = filterInvocation.httpRequest.requestURI.substring(filterInvocation.httpRequest.contextPath.length())
    	log.debug "requestedUrl: ${requestedUrl}"
    	
    	def url
        GrailsWebRequest webRequest = new GrailsWebRequest(filterInvocation.httpRequest, filterInvocation.httpResponse, ServletContextHolder.servletContext)
        WebUtils.storeGrailsWebRequest(webRequest)
        Map backupParameters  = new HashMap(webRequest.getParams())
    	urlMappingsHolder.matchAll(requestedUrl)?.each { candidate ->
        	webRequest.getParams().clear()
            webRequest.getParams().putAll(backupParameters)
         	candidate.configure(webRequest)
         	def actionName = candidate.getActionName() == null ? "" : candidate.getActionName()
        	def controllerName = candidate.getControllerName()
         	def controller = WebUtils.lookupApplication(ServletContextHolder.servletContext).getArtefactForFeature(ControllerArtefactHandler.TYPE, "${WebUtils.SLASH}${controllerName}${WebUtils.SLASH}${actionName}")
         	if (!url && controller != null)  {
                log.debug "Found UrlMappingInfo: ${candidate}"
                if (!actionName || actionName == 'null') {
                    actionName = 'index'
                }
                url = "${WebUtils.SLASH}${controllerName}${WebUtils.SLASH}${actionName}".trim()
         	}
    	}
        WebUtils.clearGrailsWebRequest()
        
        if (!url) {
	    	url = requestedUrl
        }

    	url = url.toLowerCase();
        int pos = url.indexOf('?');
        if(pos > 0){
            url = url.substring(0, pos);
        }

        log.debug("Considering url: ${url}")
        def narrowestEntry
        // Examine authorizations from config
        configPathMap.findAll{ pathMatcher.match(it.key, url) }.each { entry ->
        	// Use the narrowest path (e.g. /admin/index/** is narrower than /admin/**)
            log.debug "Comparing config mapping ${entry.key} to ${narrowestEntry?.key}"
        	if (!narrowestEntry || pathMatcher.match(narrowestEntry.key, entry.key)) {
        	    narrowestEntry = entry
        	    log.debug "New narrowestEntry: ${narrowestEntry}"
        	}
        }
        // Examine authorizations from each controller
        controllerPathMap.findAll{ pathMatcher.match(it.key, url) }.each { entry ->
			// Use the narrowest path (e.g. /admin/index/** is narrower than /admin/**)
            log.debug "Comparing controller mapping ${entry.key} to ${narrowestEntry?.key}"
			if (!narrowestEntry || pathMatcher.match(narrowestEntry.key, entry.key)) {
			    narrowestEntry = entry
        	    log.debug "New narrowestEntry: ${narrowestEntry}"
			}
		}

        def configAttrs = []
        if (narrowestEntry) {
	    	log.debug("Found path entry: ${narrowestEntry}")
            narrowestEntry.value.each { configAttr ->
                configAttrs += new SecurityConfig(configAttr)
            }
        } else {
            log.warn("No authorization mapping found -- rejecting access to url ${url}")
        }
        
        // Always send back a CAD, because sending back null means allow access (which is NOT what we want)
        return new ConfigAttributeDefinition(configAttrs)
    }

    def Collection getConfigAttributeDefinitions() {
        return null
    }

    
    void putControllerPath(path, roles) {
        log.debug("Adding controller path ${path}: ${roles}")
        controllerPathMap.put(path.toLowerCase(), roles)
    }
    
    void putConfigPath(path, roles) {
        log.debug("Adding conig path ${path}: ${roles}")
        configPathMap.put(path.toLowerCase(), roles)
    }
    
    boolean isMapped(path) {
        boolean found = (
                controllerPathMap.find{ pathMatcher.match(it.key, path?.toLowerCase()) } != null ||
                configPathMap.find{ pathMatcher.match(it.key, path?.toLowerCase()) } != null)
        log.debug("isMapped ${path}: ${found}")
        return found
    }
    
    void removeControllerPaths(path) {
        log.debug("Removing controller paths for ${path}")
        controllerPathMap.findAll{ pathMatcher.match(path?.toLowerCase(), it.key) }.each { entry ->
        	controllerPathMap.remove(entry.key)
        }
    }
    
    void removeConfigPaths(path) {
        log.debug("Removing config paths for ${path}")
        configPathMap.findAll{ pathMatcher.match(path?.toLowerCase(), it.key) }.each { entry ->
        	configPathMap.remove(entry.key)
        }
    }
     
}
