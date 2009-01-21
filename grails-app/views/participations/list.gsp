

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Lista Udziałów</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Główna</a></span>
            <span class="menuButton"><g:link class="create" action="create">Dodaj Udział</g:link></span>
        </div>
        <div class="body">
            <h1>Lista Udziałów</h1>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" class="id" />
                        
                   	        <g:sortableColumn property="endMark" title="Ocena Końcowa" />
                        
                   	        <g:sortableColumn property="startDateTime" title="Data rozpoczęcia" />
                        
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
