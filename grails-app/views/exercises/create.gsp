<html>
    <head>
        
        <meta name="layout" content="main" />
        <title>Create Exercise</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Exercise List</g:link></span>
        </div>
        <div class="body">
            <h1>Create Exercise</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
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
                                    <label for="description">Description:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:exerciseInstance,field:'description','errors')}">
                                    <input type="text" id="description" name="description" value="${fieldValue(bean:exerciseInstance,field:'description')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="title">Title:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:exerciseInstance,field:'title','errors')}">
                                    <input type="text" id="title" name="title" value="${fieldValue(bean:exerciseInstance,field:'title')}"/>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
