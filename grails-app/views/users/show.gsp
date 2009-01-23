<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Pokaż Użytkownika</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Główna</a></span>
            <span class="menuButton"><g:link class="list" action="list">Użytkownicy</g:link></span>
        </div>
        <div class="body">
            <h1>Wyświetlanie</h1>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${userInstance.id}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">E-mail:</td>
                            
                            <td valign="top" class="value">${userInstance.email}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Rola:</td>
                            
                            <td valign="top" class="value">${userInstance.roleToString()}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
              <span class="button"><g:link class="edit" action="edit" id="${userInstance?.id}">Edytuj</g:link></span>
              <!--<span class="button"><g:link class="delete" action="delete" id="${participationInstance?.id}" onclick="return confirm('Czy jesteś pewny?');">Usuń</g:link></span>-->
            </div>
        </div>
    </body>
</html>

