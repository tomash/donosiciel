

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Participation</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Participation List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Participation</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Participation</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${participationInstance}">
            <div class="errors">
                <g:renderErrors bean="${participationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${participationInstance?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="endMark">End Mark:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:participationInstance,field:'endMark','errors')}">
                                    <input type="text" id="endMark" name="endMark" value="${fieldValue(bean:participationInstance,field:'endMark')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="startDateTime">Start Date Time:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:participationInstance,field:'startDateTime','errors')}">
                                    <g:datePicker name="startDateTime" value="${participationInstance?.startDateTime}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
