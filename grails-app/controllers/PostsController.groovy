class PostsController {
  
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
      [ postInstanceList: Post.list( params ) ]
  }

  def show = {
      def postInstance = Post.get( params.id )

      if(!postInstance) {
          flash.message = "Post not found with id ${params.id}"
          redirect(action:list)
      }
      else { return [ postInstance : postInstance ] }
  }

  def delete = {
      def postInstance = Post.get( params.id )
      if(postInstance) {
          postInstance.delete()
          flash.message = "Post ${params.id} deleted"
          redirect(action:list)
      }
      else {
          flash.message = "Post not found with id ${params.id}"
          redirect(action:list)
      }
  }

  def edit = {
      def postInstance = Post.get( params.id )

      if(!postInstance) {
          flash.message = "Post not found with id ${params.id}"
          redirect(action:list)
      }
      else {
          return [ postInstance : postInstance ]
      }
  }

  def update = {
      def postInstance = Post.get( params.id )
      if(postInstance) {
          postInstance.properties = params
          if(!postInstance.hasErrors() && postInstance.save()) {
              flash.message = "Post ${params.id} updated"
              redirect(action:show,id:postInstance.id)
          }
          else {
              render(view:'edit',model:[postInstance:postInstance])
          }
      }
      else {
          flash.message = "Post not found with id ${params.id}"
          redirect(action:edit,id:params.id)
      }
  }

  def create = {
      def postInstance = new Post()
      postInstance.properties = params
      return ['postInstance':postInstance]
  }

  def save = {
      def postInstance = new Post(params)
      postInstance.user = session.user
      postInstance.createdAt = new Date()
      if(!postInstance.hasErrors() && postInstance.save()) {
          flash.message = "Post ${postInstance.id} created"
          redirect(controller:"participations",action:show,id:postInstance.participation.id)
      }
      else {
          render(view:'create',model:[postInstance:postInstance])
      }
  }
}
