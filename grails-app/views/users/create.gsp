<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main" />
  <title>Rejestracja</title>
</head>

<body>
  <div class="nav">
      <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Główna</a></span>
  </div>
  
  <div class="body">
    <h1>Rejestracja</h1>
    <g:hasErrors bean="${user}">
      <div class="errors">
          <g:renderErrors bean="${user}" as="list" />
      </div>
    </g:hasErrors>
    
  <g:form action="save" method="post">
    <div class="dialog">
      <p>Wypełnij poniższy formularz</p>
      <table class="userForm">
        <tr class='prop'>
          <td valign='top' style='text-align:left;' width='20%'>
            <label for='name'>Imię i Nazwisko:</label>
          </td>
          <td valign='top' style='text-align:left;' width='80%'>
            <input id="name" type='text' name='name' value='${user?.name}' />
          </td>
        </tr>
        <tr class='prop'>
          <td valign='top' style='text-align:left;' width='20%'>
            <label for='email'>Email:</label>
          </td>
          <td valign='top' style='text-align:left;' width='80%'>
            <input id="email" type='text' name='email' value='${user?.email}' />
          </td>
        </tr>
        <tr class='prop'>
          <td valign='top' style='text-align:left;' width='20%'>
            <label for='password'>Hasło:</label>
          </td>
          <td valign='top' style='text-align:left;' width='80%'>
            <input id="password" type='password' name='password' />
          </td>
        </tr>
        <tr class='prop'>
          <td valign='top' style='text-align:left;' width='20%'>
            <label for='password_confirmation'>Potwierdź hasło:</label>
          </td>
          <td valign='top' style='text-align:left;' width='80%'>
            <input id="password_confirmation" type='password' name='password_confirmation' />
          </td>
        </tr>
      </table>
    </div>
    <div class="buttons">
      <span class="formButton">
        <input type="submit" value="Zarejestruj"></input>
      </span>
      
    </div>
  </g:form>
  </div>
</body>
</html>
