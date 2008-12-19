<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Exercise List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Exercise</g:link></span>
        </div>
        <div class="body">
            <h1>Exercise List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <!--<g:sortableColumn property="description" title="Description" />-->
                        
                   	        <g:sortableColumn property="title" title="Title" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${exerciseInstanceList}" status="i" var="exerciseInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${exerciseInstance.id}">${fieldValue(bean:exerciseInstance, field:'id')}</g:link></td>
                        
                            <!--<td>${fieldValue(bean:exerciseInstance, field:'description')}</td>-->
                        
                            <td><g:link action="show" id="${exerciseInstance.id}">${fieldValue(bean:exerciseInstance, field:'title')}</g:link></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${Exercise.count()}" />
            </div>
        </div>
    </body>
</html>
