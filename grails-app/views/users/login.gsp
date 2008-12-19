<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main" />
  <title>Logowanie</title>
</head>

<body>
  <div class="nav">
      <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Główna</a></span>
  </div>
  
  <div class="body">
    <h1>Logowanie</h1>
    
  <g:form action="authenticate" method="post">
    <div class="dialog">
      <p>Wpisz swój email i hasło</p>
      <table class="userForm">
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
          <input id="password" type='password' name='password' value='${user?.password}' />
        </td>
      </tr>
      </table>
    </div>
    <div class="buttons">
      <span class="formButton">
      <input type="submit" value="Zaloguj"></input>
      </span>
    </div>
  </g:form>
  </div>
</body>
</html>
