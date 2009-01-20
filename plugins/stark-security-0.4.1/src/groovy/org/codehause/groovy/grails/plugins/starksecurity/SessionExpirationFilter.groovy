package org.codehaus.groovy.grails.plugins.starksecurity

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.util.Assert

class SessionExpirationFilter implements Filter, InitializingBean {
    static log = LogFactory.getLog("org.codehaus.groovy.grails.plugins.starksecurity.SessionExpirationFilter")
    def private String expiredRedirectUrl

    public void afterPropertiesSet() throws Exception {
//        Assert.hasText(expiredRedirectUrl, "expiredRedirectUrl required")
    }

    public void init(FilterConfig arg0) throws ServletException {}
    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	
    	if (expiredRedirectUrl && expiredRedirectUrl.length() > 0) {
	        Assert.isInstanceOf(HttpServletRequest.class, request, "Can only process HttpServletRequest")
	        Assert.isInstanceOf(HttpServletResponse.class, response, "Can only process HttpServletResponse")
	
	        HttpServletRequest httpRequest = (HttpServletRequest) request
	        HttpServletResponse httpResponse = (HttpServletResponse) response
	        log.debug("Requested URI: ${request.getRequestURI()}")
	        
	        HttpSession session = httpRequest.getSession(true)
	        log.debug("Session: ${session}")
	
	        log.debug("Requested session id: ${httpRequest.getRequestedSessionId()}, is valid: ${httpRequest.isRequestedSessionIdValid()}")
	        if (httpRequest.getRequestedSessionId() != null &&
	            !httpRequest.isRequestedSessionIdValid() &&
	            !isSessionlessUri(httpRequest))
	        {    
	            String targetUrl = httpRequest.getContextPath() + expiredRedirectUrl
	            log.debug("Session expired, redirecting to ${targetUrl}")
	            httpResponse.sendRedirect(httpResponse.encodeRedirectURL(targetUrl))
	            return
	        }
	        log.debug("Session active, allowing through")
    	} else {
    		log.debug("No expired session redirect URL set, allowing through")
    	}

        chain.doFilter(request, response)
    }
    
    private boolean isSessionlessUri(HttpServletRequest httpRequest) {
        String uri = httpRequest.getRequestURI()
        return uri in [
                httpRequest.getContextPath() + '/',
                httpRequest.getContextPath() + expiredRedirectUrl,
        		httpRequest.getContextPath() + "${ConfigurationHolder.config.starksecurity.logoutUrl}"
        ]
    }

    public void setExpiredRedirectUrl(String expiredRedirectUrl) {
        this.expiredRedirectUrl = expiredRedirectUrl ? expiredRedirectUrl.trim() : null
    }
}
