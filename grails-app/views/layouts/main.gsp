<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <g:layoutHead />
        <g:javascript library="application" />		
    </head>
    <body>
      <div id="wrapper">
        <div id="header">
          <div class="left"> </div>
          <div class="right"> </div>
          <div id="spinner" class="spinner" style="display:none;">
              <img src="${createLinkTo(dir:'images',file:'spinner.gif')}" alt="Spinner" />
          </div>	
          <div class="logo">
            <!--<img src="${createLinkTo(dir:'images',file:'donosiciel_logo.jpg')}" alt="Donosiciel" />-->
            <h1>Donosiciel</h1>
          </div>
        </div>
        
        <div id="left_menu">
          <div id="menu_header">Menu Główne</div>
          <ul id="main_menu_list">
            <li><g:link controller="exercises">Ćwiczenia i ich opisy</g:link></li>
            <li><g:link controller="participations">Udziały w ćwiczeniach (instancje)</g:link></li>
            <li><g:link controller="users">Logowanie i rejestracja</g:link></li>
            <g:if test="${session.user}">
              <li><g:link controller="users" action="edit" id="${session.user.id}">Zmiana hasła</g:link></li>
            </g:if>
            <g:if test="${session.user?.role == 16}">
            <li><g:link controller="userAdmin">Administracja użytkownikami</g:link></li>
          </g:if>
          </ul>
        </div>
        
        <div id="content">
          <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
          </g:if>
          
          <g:if test="${session.user}">
            Zalogowany jako ${session.user.name} (${session.user.email}).
            <g:link controller="users" action="logout">Wyloguj</g:link>
            |
            <g:link controller="users" action="edit" id="${session.user.id}">Zmiana hasła</g:link>
          </g:if>
          <g:else>
            Niezalogowany.
            <g:link controller="users" action="login">Logowanie</g:link>
            |
            <g:link controller="users" action="create">Rejestracja</g:link>
          </g:else>
          
          <g:layoutBody />
        
        <br class="clear" />
        </div>
        <br class="clear" />
        <div id="footer">
          <div class="left"> </div>
          <div class="right"> </div>
        </div>
      </div>
    </body>	
</html>