class UsersController {
  
  def index = { redirect(action:login) }
  
  def login = {
  }
  
  def logout = {
    session.user = null
    flash.message = "Wylogowany!"
    redirect(uri:'/')
  }
  
  def authenticate = {
    def user = User.findByEmail(params.email) 
    if (user) 
    { 
      if (user.authenticate(params.password)) 
      {
        flash.message = "Użytkownik ${user.email} zalogowany"
        session.user = user
        if(session["original_request"])
          redirect(controller:session["original_request"]["controller"],
            action:session["original_request"]["action"],
            params:session["original_request"])
        else
          redirect(uri:'/')
      }
      else 
      { 
        flash.message = "Nieprawidłowe hasło dla użytkownika ${user.email}"
        redirect(action:'login')   
      } 
    } 
    else 
    { 
      flash.message = "Nie mogę znaleźć użytkownika ${params.email}"  
      redirect(action:login) 
    } 
  }
  

  def register = {}

  //def handleRegistration = { def user = new User(params) if (params.password != params.confirm) { flash.message = "The passwords you entered do not match." redirect(action:register) } else { user.password = params.password.encodeAsPassword() if (user.save()) { redirect(controller:'topic', action:'list') } else { flash.user = user redirect(action:register) } } } 

}
