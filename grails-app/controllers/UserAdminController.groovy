@Mixin(ApplicationController)
class UserAdminController {

  def beforeInterceptor = [action:this.&require_admin_logged_in]

  
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
  
  def edit = {
      def userInstance = User.get( params.id )

      if(!userInstance) {
          flash.message = "Nie znaleziono użytkownika o ${params.id}"
          redirect(action:list)
      }
      else {
          return [ userInstance : userInstance ]
      }
  }

  def update = {
      def userInstance = User.get( params.id )
      if(userInstance) {
          userInstance.properties = params
          if(!userInstance.hasErrors() && userInstance.save()) {
              flash.message = "Użytkownik ${params.id} został zmieniony"
              redirect(action:show,id:userInstance.id)
          }
          else {
              render(view:'edit',model:[userInstance:userInstance])
          }
      }
      else {
          flash.message = "Nie znaleziono użytkownika o ${params.id}"
          redirect(action:edit,id:params.id)
      }
  }
}
