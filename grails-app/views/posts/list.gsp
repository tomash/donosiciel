

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Post List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Post</g:link></span>
        </div>
        <div class="body">
            <h1>Post List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="content" title="Content" />
                        
                   	        <g:sortableColumn property="createdAt" title="Created At" />
                        
                   	        <th>Participation</th>
                   	    
                   	        <th>User</th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${postInstanceList}" status="i" var="postInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${postInstance.id}">${fieldValue(bean:postInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:postInstance, field:'content')}</td>
                        
                            <td>${fieldValue(bean:postInstance, field:'createdAt')}</td>
                        
                            <td>${fieldValue(bean:postInstance, field:'participation')}</td>
                        
                            <td>${fieldValue(bean:postInstance, field:'user')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${Post.count()}" />
            </div>
        </div>
    </body>
</html>
