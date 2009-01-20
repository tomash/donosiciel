package org.codehaus.groovy.grails.plugins.starksecurity

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationListener
import org.springframework.context.ApplicationEvent
import org.springframework.security.event.authentication.*
import org.springframework.security.event.authorization.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.apache.commons.logging.LogFactory

class StarkSecurityEventListener implements ApplicationListener, ApplicationContextAware {
	
    static log = LogFactory.getLog("org.codehaus.groovy.grails.plugins.starksecurity.StarkSecurityEventListener")
    
    private ApplicationContext appCtx
    
    void setApplicationContext(ApplicationContext newCtx) {
        this.appCtx = newCtx
    }

    void onApplicationEvent(final ApplicationEvent e) {
		if (e instanceof AbstractAuthenticationEvent || e instanceof AbstractAuthorizationEvent) {
			def eventLongName = e.class.name
			log.debug "eventLongName: ${eventLongName}"
			def eventName = eventLongName.substring(eventLongName.lastIndexOf('.') + 1)
			log.debug "eventName: ${eventName}"
			ConfigurationHolder.config?.starksecurity?.eventHandlers?.each { handlerName, eventHandler ->
				log.debug "Checking handler ${handlerName}"
				def handlerClass = Class.forName("org.springframework.security.event.${handlerName}")
				if (handlerClass && handlerClass.isAssignableFrom(e.class)) {
					log.debug "Found appropriate handler ${handlerName}"
					eventHandler.call(e, this.appCtx)
				}
			}
		}
   	}
}