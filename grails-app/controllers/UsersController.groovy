@Mixin(ApplicationController)
class UsersController {
  
  def beforeInterceptor = [action:this.&require_logged_in,only: ['edit','change_password']]
  
  
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
/*
  def save = 
  { 
    params.remove('role') // nice try, fuckers!
    def user = new User(params) 
    if (params.password != params.password_confirmation) 
    { 
      flash.message = "Hasło i potwierdzenie się nie zgadzają." 
      redirect(action:create) 
    }
    else 
    { 
      user.register_magic()
      if (user.save()) 
      {
        flash.message = "Rejestracja udana, możesz się zalogować!"
        redirect(uri:'/') 
      } 
      else 
      { 
        flash.user = user
        render(view:'create',model:[user:user])
        //redirect(action:create) 
      } 
    } 
  }
*/
  def save = 
  { 
    params.remove('role') // nice try, fuckers!
    def userInstance = new User(params) 
    def valid = userInstance.validate()
    if (params.password != params.password_confirmation) 
    { 
        userInstance.errors.rejectValue("password","message.code","Hasło i potwierdzenie są różne")
        valid = false
    } 
    userInstance.register_magic()
    if (valid && userInstance.save()) 
    {
        flash.message = "Rejestracja udana - możesz się zalogować!"
        redirect(uri:'/') 
    }
    else 
    { 
        flash.user = userInstance
        render(view:'create',model:[user:userInstance])
    } 
  }
  
  def edit = 
  {
    def userInstance = User.get(params.id)
    if(!userInstance.authorize(session.user))
    {
      flash.message = "Próba nieautoryzowanego dostępu"
      redirect(uri:"/")
      return false
    }
    
    return ['userInstance':userInstance]
  }
  
  def change_password = 
  {
    def userInstance = User.get(params.id)
    def valid = userInstance.validate()

    if(!userInstance.authorize(session.user))
    {
      flash.message = "Próba nieautoryzowanego dostępu"
      redirect(uri:"/")
      return false
    }
    
    if (params.password != params.password_confirmation) 
    { 
      userInstance.errors.rejectValue("password","message.code","Hasło i potwierdzenie hasła mają różne wartości")
      valid = false
    }
    
    userInstance.password = params.password
    userInstance.register_magic()
    if (valid && userInstance.save()) 
    {
        flash.message = "Hasło zostało zmienione"
        redirect(uri:'/') 
    }
    else 
    { 
        flash.user = userInstance
        render(view:'edit',model:[user:userInstance])
    }
    
  }

}
