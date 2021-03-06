<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Exercise</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Exercise List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Exercise</g:link></span>
        </div>
        <div class="body">
            <h1>Edytuj Ćwiczenie</h1>
            <g:hasErrors bean="${exerciseInstance}">
            <div class="errors">
                <g:renderErrors bean="${exerciseInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" action="update" >
                <input type="hidden" name="id" value="${exerciseInstance?.id}" />
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
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Aktualizuj" /></span>
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
