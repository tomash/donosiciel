<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Dodaj Udział</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Głowna</a></span>
            <span class="menuButton"><g:link class="list" action="list">Udziały</g:link></span>
        </div>
        <div class="body">
            <h1>Stwórz udział</h1>
            <g:hasErrors bean="${participationInstance}">
            <div class="errors">
                <g:renderErrors bean="${participationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="endMark">Ocena końcowa:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:participationInstance,field:'endMark','errors')}">
                                    <input type="text" id="endMark" name="endMark" value="${fieldValue(bean:participationInstance,field:'endMark')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="startDateTime">Dzień rozpoczęcia:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:participationInstance,field:'startDateTime','errors')}">
                                    <g:datePicker name="startDateTime" precision="day" value="${participationInstance?.startDateTime}" ></g:datePicker>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                              <td valign="top" class="name">
                                <label for="exercise">Ćwiczenie:</label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean:participationInstance,field:'exercise','errors')}">
                                <g:select optionKey="id" from="${Exercise.list()}" name="exercise.id" value="${participationInstance?.exercise?.id}" ></g:select>
                              </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Dodaj" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
