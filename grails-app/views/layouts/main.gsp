<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <g:layoutHead />
        <g:javascript library="application" />		
        <g:javascript library="prototype" />
        <g:javascript library="scriptaculous" />
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${createLinkTo(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>	
        <div class="logo"><img src="${createLinkTo(dir:'images',file:'donosiciel_logo.jpg')}" alt="Donosiciel" /></div>
        <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
        </g:if>
        
        <g:if test="${session.user}">
          Zalogowany jako ${session.user.email}.
          <g:link controller="users" action="logout">Wyloguj</g:link>
        </g:if>
        <g:else>
          Niezalogowany.
          <g:link controller="users" action="login">Zaloguj</g:link>
        </g:else>
        
        <g:layoutBody />		
    </body>	
</html>