package org.codehaus.groovy.grails.plugins.starksecurity

import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.util.Assert
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class UtilityFilter implements Filter, InitializingBean, ApplicationContextAware {
    static log = LogFactory.getLog("org.codehaus.groovy.grails.plugins.starksecurity.UtilityFilter")

    private ApplicationContext appCtx
    private Closure handler
    
    public UtilityFilter(Closure handler) {
        this.handler = handler
    }
    
    void setApplicationContext(ApplicationContext newCtx) {
        this.appCtx = newCtx
    }
    
    void setHandler(Closure newHandler) {
        this.handler = newHandler
    }
    
    public void afterPropertiesSet() throws Exception {
    }

    public void init(FilterConfig arg0) throws ServletException {}
    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        if (handler) {
            handler.call(request, response, chain, appCtx)
        } else {
            chain.doFilter(request, response)
        }
    }
    
}
