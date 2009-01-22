<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Użytkownicy</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(uri:'/')}">Główna</a></span>
            <span class="menuButton"><g:link class="list" action="list">Użytkownicy</g:link></span>
        </div>
        <div class="body">
            <h1>Użytkownicy</h1>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="email" title="Login" />
                            
                            <g:sortableColumn property="role" title="Rola" />
                            
                            <th class="options">opcje</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${userList}" status="i" var="userInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${userInstance.id}">${userInstance.id}</g:link></td>
                        
                            <td><g:link action="show" id="${userInstance.id}">${userInstance.email}</g:link></td>
                            
                            <td>${userInstance.roleToString()}</td>
                            
                            <td>
                              <g:link action="show" id="${userInstance.id}">pokaż</g:link>
                              <g:link action="edit" id="${userInstance.id}">edytuj</g:link>
                            </td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${User.count()}" />
            </div>
            
        </div>
    </body>
</html>
