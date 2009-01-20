class UserAdminController {

  def beforeInterceptor = [action:this.&checkUser]

  def checkUser() {
    if(!session.user) 
    {
      // i.e. user not logged in
      redirect(controller:'users',action:'login')
      return false
    }
    if(session.user.role < 16)
    {
      // i.e. user is not an admin
      flash['message'] = "Próba nieautoryzowanego dostępu"
      redirect(uri:'/')
      return false
    }
  }
  
  def index = { redirect(action:list) }
  
  def list = {
    if(!params.max) params.max = 10
      [ userList: User.list( params ) ]
  }
  
  def show = {
    def userInstance = User.get( params.id )

    if(!userInstance) {
        flash.message = "Brak użytkownika o id ${params.id}"
        redirect(action:list)
    }
    else { return [ userInstance : userInstance ] }
  }
}
