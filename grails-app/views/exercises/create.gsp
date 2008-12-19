<html>
    <head>
        
        <meta name="layout" content="main" />
        <title>Dodaj Ćwiczenie</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Główna</a></span>
            <span class="menuButton"><g:link class="list" action="list">Ćwiczenia</g:link></span>
        </div>
        <div class="body">
            <h1>Dodaj ćwiczenie</h1>
            <g:hasErrors bean="${exerciseInstance}">
            <div class="errors">
                <g:renderErrors bean="${exerciseInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                          <tr class="prop">
                              <td valign="top" class="name">
                                  <label for="title">Tytuł:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:exerciseInstance,field:'title','errors')}">
                                  <input type="text" id="title" name="title" value="${fieldValue(bean:exerciseInstance,field:'title')}"/>
                              </td>
                          </tr> 
                      
                          <tr class="prop">
                              <td valign="top" class="name">
                                  <label for="description">Opis:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:exerciseInstance,field:'description','errors')}">
                                  <textarea id="description" name="description">${fieldValue(bean:exerciseInstance,field:'description')}</textarea>
                              </td>
                          </tr> 
                        </tbody>
                    </table>
                
                
                
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Dodaj" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
