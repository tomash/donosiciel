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
        if(session && session["original_request"])
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
  /*
  def create = {
      def exerciseInstance = new Exercise()
      exerciseInstance.properties = params
      return ['exerciseInstance':exerciseInstance]
  }

  def save = {
      def exerciseInstance = new Exercise(params)
      if(!exerciseInstance.hasErrors() && exerciseInstance.save()) {
          flash.message = "Ćwiczenie ${exerciseInstance.id} dodane"
          redirect(action:show,id:exerciseInstance.id)
      }
      else {
          render(view:'create',model:[exerciseInstance:exerciseInstance])
      }
  }
*/
  def create = 
  {
    def userInstance = new User()
    userInstance.properties = params
    return ['userInstance':userInstance]
  }

  def save = 
  { 
    def user = new User(params) 
    if (params.password != params.password_confirmation) 
    { 
      flash.message = "Hasło i potwierdzenie się nie zgadzają." 
      redirect(action:create) 
    } 
    else 
    { 
      user.register_magic
      if (user.save()) 
      {
        flash.message = "Rejestracja udana, możesz się zalogować!"
        redirect(uri:'/') 
      } 
      else 
      { 
        flash.user = user 
        redirect(action:create) 
      } 
    } 
  } 

}
