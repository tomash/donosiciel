<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Pokaż Ćwiczenie</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Główna</a></span>
            <span class="menuButton"><g:link class="list" action="list">Ćwiczenia</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Dodaj Ćwiczenie</g:link></span>
        </div>
        <div class="body">
            <h1>Pokaż Ćwiczenie</h1>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:exerciseInstance, field:'id')}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">Tytuł:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:exerciseInstance, field:'title')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Opis:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:exerciseInstance, field:'description')}</td>
                            
                        </tr>
                    <!--
                        <tr class="prop">
                            <td valign="top" class="name">Participations:</td>
                            
                            <td  valign="top" style="text-align:left;" class="value">
                                <ul>
                                <g:each var="p" in="${exerciseInstance.participations}">
                                    <li><g:link controller="participation" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    -->
                        
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
              <span class="button"><g:link class="edit" action="edit" id="${exerciseInstance?.id}">Edytuj</g:link></span>
              <span class="button"><g:link class="delete" action="delete" id="${participationInstance?.id}" onclick="return confirm('Czy jesteś pewny?');">Usuń</g:link></span>
            </div>
            
            <h2>Udziały (instancje) ćwiczenia</h2>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                   	        <g:sortableColumn property="id" title="Id" class="id" />
                            <g:sortableColumn property="startDateTime" title="rozpoczęte" class="startDateTime" />
                   	        <!--<g:sortableColumn property="title" title="Nazwa" />-->
                            <th>Studenci</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${exerciseInstance.participations}" status="i" var="participationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" controller="participations" id="${participationInstance.id}" style="width:100%; height: 100%; display:block; font-size: 14px; text-align: center;">${fieldValue(bean:participationInstance, field:'id')}</g:link></td>
                        
                            <td>
                              <g:link action="show" controller="participations" id="${participationInstance.id}" style="width:100%; height: 100%; display:block; font-size: 12px;"> ${participationInstance.startDateTime} </g:link>
                            </td>
                            
                            <td>
                              <ul id="students_list">
                                <g:each in="${participationInstance.students}" status="j" var="studentInstance">
                                  <li>${studentInstance}</li>
                                </g:each>
                              </ul>
                            </td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="buttons" style="padding: 5px 0;">
                <span class="menuButton">
                  <span class="menuButton"><g:link class="create" action="create" controller="participations" params="[exercise_id:7]">Dodaj Udział do tego Ćwiczenia</g:link></span>
                </span>
            </div>
            
        </div>
    </body>
</html>
