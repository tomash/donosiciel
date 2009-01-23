<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main" />
  <title>Edycja</title>
</head>

<body>
  <div class="nav">
      <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Główna</a></span>
  </div>
  
  <div class="body">
    <h1>Zmiana hasła</h1>
    
    <g:form action="change_password" method="post" id="${userInstance.id}">
    <div class="dialog">
      <table class="userForm">
        <tr class='prop'>
          <td valign='top' style='text-align:left;' width='20%'>
            <label for='password'>Nowe Hasło:</label>
          </td>
          <td valign='top' style='text-align:left;' width='80%'>
            <input id="password" type='password' name='password' />
          </td>
        </tr>
        <tr class='prop'>
          <td valign='top' style='text-align:left;' width='20%'>
            <label for='password_confirmation'>Potwierdź nowe hasło:</label>
          </td>
          <td valign='top' style='text-align:left;' width='80%'>
            <input id="password_confirmation" type='password' name='password_confirmation' />
          </td>
        </tr>
      </table>
    </div>
    <div class="buttons">
      <span class="formButton">
        <input type="submit" value="Zapisz"></input>
      </span>
      
    </div>
  </g:form>
  </div>
</body>
</html>
