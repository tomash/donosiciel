

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Udział w ćwiczeniu</title>
        <g:javascript library="prototype" />
        <g:javascript library="scriptaculous" />
        
        <g:javascript>
          window.onload = function()
                          {
                            new Ajax.Autocompleter("student_email",
                                                   "autocomplete_choices",
                                                   "/donosiciel/participations/ajaxAutoComplete",
                                                   {}
                                                  );
                          }
        </g:javascript>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Główna</a></span>
            <span class="menuButton"><g:link class="list" action="list">Udziały</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Dodaj Udział</g:link></span>
        </div>
        <div class="body">
            <h1>Udział #${participationInstance?.id}</h1>
            <div class="dialog">
                <table>
                    <tbody>

                        <tr class="prop">
                            <td valign="top" class="name">Ćwiczenie:</td>
                            
                            <td valign="top" class="value"><g:link class="show" action="show" controller="exercises" id="${participationInstance?.exercise?.id}">${participationInstance.exercise}</g:link></td>
                        </tr>
                    
                    
                        <tr class="prop">
                            <td valign="top" class="name">Prowadzący:</td>
                            
                            <td valign="top" class="value">${participationInstance.user}</td>
                        </tr>
                    <!--
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${participationInstance.id}</td>
                        </tr>
                    -->
                        <tr class="prop">
                            <td valign="top" class="name">Ocena Końcowa:</td>
                            
                            <td valign="top" class="value">${participationInstance.endMark}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Rozpoczęte:</td>
                            <td valign="top" class="value">
                              <g:formatDate format="yyyy-MM-dd" date="${participationInstance.startDateTime}" />
                            </td>
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            
            <div class="buttons">
              <span class="button"><g:link class="edit" action="edit" id="${participationInstance?.id}">Edytuj</g:link></span>
              <span class="button"><g:link class="delete" action="delete" id="${participationInstance?.id}" onclick="return confirm('Czy jesteś pewny?');">Usuń</g:link></span>
            </div>
            
            <h3>Studenci:</h3>
            <ul id="students_list">
              <g:each in="${participationInstance.students}" status="i" var="studentInstance">
              <li>${studentInstance}</li>
              </g:each>
            </ul>
            
            <g:if test="${session.user.role>1}">
              <a href="#" onclick="Element.blindDown('add_student_form')">dodaj studenta do udziału</a>
            </g:if>
            <div id="add_student_form" style="display:none;">
              <g:form action="save_student" method="post" id="${participationInstance?.id}" >
                <input type="text" id="student_email" name="student_email"/>
                <input class="save" type="submit" value="Dodaj" />
              </g:form>
            </div>
            
            <div id="autocomplete_choices" class="autocomplete" ></div>
            
            <h2>Wiadomości:</h2>
            <ul id="posts_list">
            <g:each in="${postsList?}" status="i" var="post">
              <li>
                <div class="header">Dodane przez: <b>${post.user}</b> <i>(${post.createdAt})</i></div>
                <div class="content">${post.content.encodeAsHTML()}</div>
                <g:if test="${post.filename}">
                  <div class="file">załączono: 
                  <a href="${createLinkTo( dir:'uploaded/' + participationInstance?.id, file: post.filename, absolute:false )}" target="_new">${post.filename}</a>
                  </div>
                </g:if>

              </li>
            </g:each>
            </ul>
            
            
            <h2>Dodaj wiadomość</h2>
            <g:hasErrors bean="${postInstance}">
            <div class="errors">
                <g:renderErrors bean="${postInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form controller="posts" action="save" method="post" enctype="multipart/form-data" >
                <input type="hidden" id="participation_id" name="participation.id" value="${fieldValue(bean:participationInstance,field:'id')}"/>
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="content">Treść:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:postInstance,field:'content','errors')}">
                                    <textarea id="content" name="content">${fieldValue(bean:postInstance,field:'content')}</textarea>
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="content">Plik:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:postInstance,field:'uploaded','errors')}">
                                    <input type="file" id="file" name="uploaded_file" size="50"/>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Dodaj wiadomość" /></span>
                </div>
            </g:form>
        </div>
            
            
        </div>
    </body>
</html>
