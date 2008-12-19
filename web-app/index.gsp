<html>
    <head>
        <title>Witaj w Donosicielu</title>
		<meta name="layout" content="main" />
    </head>
    <body>
        <h1 style="margin-left:20px;">Donosiciel on Groovy on Grails ;)</h1>
        <p style="margin-left:20px;width:80%">[póki aplikacja jest w fazie rozwojowej]: Lista kontrolerów:</p>
        <div class="dialog" style="margin-left:20px;width:60%;">
            <ul>
              <g:each var="c" in="${grailsApplication.controllerClasses}">
                    <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
              </g:each>
            </ul>
        </div>
    </body>
</html>