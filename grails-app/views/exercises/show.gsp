

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Pokaż Ćwiczenie</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Główna</a></span>
            <span class="menuButton"><g:link class="list" action="list">Ćwiczenia</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Dodaj Ćwiczenie</g:link></span>
        </div>
        <div class="body">
            <h1>Pokaż Ćwiczenie</h1>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:exerciseInstance, field:'id')}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">Tytuł:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:exerciseInstance, field:'title')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Opis:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:exerciseInstance, field:'description')}</td>
                            
                        </tr>
                    <!--
                        <tr class="prop">
                            <td valign="top" class="name">Participations:</td>
                            
                            <td  valign="top" style="text-align:left;" class="value">
                                <ul>
                                <g:each var="p" in="${exerciseInstance.participations}">
                                    <li><g:link controller="participation" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    -->
                        
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
              <span class="button"><g:link class="edit" action="edit" id="${exerciseInstance?.id}">Edytuj</g:link></span>
              <span class="button"><g:link class="delete" action="delete" id="${participationInstance?.id}" onclick="return confirm('Czy jesteś pewny?');">Usuń</g:link></span>
            </div>
        </div>
    </body>
</html>

            <g:if test="${menuInstance && menuInstance.id}">
              <span class="menuButton"><g:link class="edit" action="edit" id="${menuInstance?.id}">Edytuj pozycję</g:link></span>
              <span class="menuButton"><g:link class="delete" action="delete" id="${menuInstance?.id}" onclick="return confirm('Czy jesteś pewny?');">Usuń pozycję</g:link></span>
            </g:if>
