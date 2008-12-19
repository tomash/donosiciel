

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Udział</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Główna</a></span>
            <span class="menuButton"><g:link class="list" action="list">Udziały</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Dodaj Udział</g:link></span>
        </div>
        <div class="body">
            <h1>Pokaż Udział</h1>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:participationInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Ocena Końcowa:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:participationInstance, field:'endMark')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Rozpoczęte:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:participationInstance, field:'startDateTime')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
              <span class="button"><g:link class="edit" action="edit" id="${participationInstance?.id}">Edytuj</g:link></span>
              <span class="button"><g:link class="delete" action="delete" id="${participationInstance?.id}" onclick="return confirm('Czy jesteś pewny?');">Usuń</g:link></span>
            </div>
            
            <h2>Wiadomości:</h2>
            <ul>
            <g:each in="${postsList?}" status="i" var="post">
              <li>${fieldValue(bean:post, field:'content')} (${fieldValue(bean:post, field:'createdAt')})</li>
            </g:each>
            </ul>
            
            
            <h2>Dodaj wiadomość</h2>
            <g:hasErrors bean="${postInstance}">
            <div class="errors">
                <g:renderErrors bean="${postInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form controller="posts" action="save" method="post" >
                <input type="hidden" id="participation_id" name="participation.id" value="${fieldValue(bean:participationInstance,field:'id')}"/>
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="content">Treść:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:postInstance,field:'content','errors')}">
                                    <textarea id="content" name="content">${fieldValue(bean:postInstance,field:'content')}</textarea>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Dodaj wiadomość" /></span>
                </div>
            </g:form>
        </div>
            
            
        </div>
    </body>
</html>
