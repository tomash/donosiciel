<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Ćwiczenia</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Główna</a></span>
            <span class="menuButton"><g:link class="create" action="create">Dodaj Ćwiczenie</g:link></span>
        </div>
        <div class="body">
            <h1>Lista Ćwiczeń</h1>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <!--<g:sortableColumn property="description" title="Description" />-->
                        
                   	        <g:sortableColumn property="title" title="Nazwa" />
                        
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
            <!--<div class="buttons">
              <span class="menuButton"><g:link class="create" action="create">Dodaj Ćwiczenie</g:link></span>
            </div>-->
        </div>
    </body>
</html>
