

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Participation List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Participation</g:link></span>
        </div>
        <div class="body">
            <h1>Participation List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="endMark" title="End Mark" />
                        
                   	        <g:sortableColumn property="startDateTime" title="Start Date Time" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${participationInstanceList}" status="i" var="participationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${participationInstance.id}">${fieldValue(bean:participationInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:participationInstance, field:'endMark')}</td>
                        
                            <td>${fieldValue(bean:participationInstance, field:'startDateTime')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${Participation.count()}" />
            </div>
        </div>
    </body>
</html>
