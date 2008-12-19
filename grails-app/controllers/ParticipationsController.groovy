class ParticipationsController {
  
  def beforeInterceptor = [action:this.&checkUser,except: ['index','list','show']]

  def checkUser() {
    if(!session.user) {
      // i.e. user not logged in
      redirect(controller:'users',action:'login')
      return false
    }
  }

  def index = { redirect(action:list,params:params) }

  // the delete, save and update actions only accept POST requests
  def allowedMethods = [delete:'POST', save:'POST', update:'POST']

  def list = {
      if(!params.max) params.max = 10
      [ participationInstanceList: Participation.list( params ) ]
  }

  def show = {
      def participationInstance = Participation.get( params.id )
      def postsList = Post.findAll("from Post as p where p.participation=? order by createdAt ASC", [participationInstance])
      
      if(!participationInstance) {
          flash.message = "Participation not found with id ${params.id}"
          redirect(action:list)
      }
      else { return [ participationInstance : participationInstance, postsList : postsList ] }
  }

  def delete = {
      def participationInstance = Participation.get( params.id )
      if(participationInstance) {
          participationInstance.delete()
          flash.message = "Participation ${params.id} deleted"
          redirect(action:list)
      }
      else {
          flash.message = "Participation not found with id ${params.id}"
          redirect(action:list)
      }
  }

  def edit = {
      def participationInstance = Participation.get( params.id )

      if(!participationInstance) {
          flash.message = "Participation not found with id ${params.id}"
          redirect(action:list)
      }
      else {
          return [ participationInstance : participationInstance ]
      }
  }

  def update = {
      def participationInstance = Participation.get( params.id )
      if(participationInstance) {
          participationInstance.properties = params
          if(!participationInstance.hasErrors() && participationInstance.save()) {
              flash.message = "Participation ${params.id} updated"
              redirect(action:show,id:participationInstance.id)
          }
          else {
              render(view:'edit',model:[participationInstance:participationInstance])
          }
      }
      else {
          flash.message = "Participation not found with id ${params.id}"
          redirect(action:edit,id:params.id)
      }
  }

  def create = {
      def participationInstance = new Participation()
      participationInstance.properties = params
      return ['participationInstance':participationInstance]
  }

  def save = {
      def participationInstance = new Participation(params)
      if(!participationInstance.hasErrors() && participationInstance.save()) {
          flash.message = "Participation ${participationInstance.id} created"
          redirect(action:show,id:participationInstance.id)
      }
      else {
          render(view:'create',model:[participationInstance:participationInstance])
      }
  }
}
