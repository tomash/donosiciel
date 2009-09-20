<html>
    <head>
        <title>ILF: Donosiciel</title>
		<meta name="layout" content="main" />
    </head>
    <body>
        <h1 style="margin-left:20px;">Donosiciel on Grails ;)</h1>
        
        <h3>Sekcje serwisu</h3>
        <ul id="sections">
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
        
        <p style="margin-left:20px;width:80%;display: none;">[póki aplikacja jest w fazie rozwojowej]: Lista kontrolerów:</p>
        <div class="dialog" style="margin-left:20px;width:60%;display: none;">
          <ul>
            <g:each var="c" in="${grailsApplication.controllerClasses}">
                  <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
            </g:each>
          </ul>
        </div>
    </body>
</html>