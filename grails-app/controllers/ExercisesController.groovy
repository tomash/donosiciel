class ExercisesController {

  def beforeInterceptor = [action:this.&checkUser,except: ['index','list','show']]

  def checkUser() {
    if(!session.user) 
    {
      // i.e. user not logged in
      redirect(controller:'users',action:'login')
      return false
    }
    if(session.user.role < 2)
    {
      // i.e. user is not a teacher or admin
      flash['message'] = "Próba nieautoryzowanego dostępu"
      redirect(uri:'/')
      return false
    }
  }

  def index = { redirect(action:list,params:params) }

  // the delete, save and update actions only accept POST requests
  static allowedMethods = [delete:'POST', save:'POST', update:'POST']

  def list = {
      if(!params.max) params.max = 10
      [ exerciseInstanceList: Exercise.list( params ) ]
  }

  def show = {
      def exerciseInstance = Exercise.get( params.id )

      if(!exerciseInstance) {
          flash.message = "Nie znaleziono ćwiczenia o id ${params.id}"
          redirect(action:list)
      }
      else { return [ exerciseInstance : exerciseInstance ] }
  }

  def delete = {
      def exerciseInstance = Exercise.get( params.id )
      if(exerciseInstance) {
          exerciseInstance.delete()
          flash.message = "Ćwiczenie ${params.id} zostało usunięte"
          redirect(action:list)
      }
      else {
          flash.message = "Nie znaleziono ćwiczenia o ${params.id}"
          redirect(action:list)
      }
  }

  def edit = {
      def exerciseInstance = Exercise.get( params.id )

      if(!exerciseInstance) {
          flash.message = "Nie znaleziono ćwiczenia o ${params.id}"
          redirect(action:list)
      }
      else {
          return [ exerciseInstance : exerciseInstance ]
      }
  }

  def update = {
      def exerciseInstance = Exercise.get( params.id )
      if(exerciseInstance) {
          exerciseInstance.properties = params
          if(!exerciseInstance.hasErrors() && exerciseInstance.save()) {
              flash.message = "Ćwiczenie ${params.id} zostało zmienione"
              redirect(action:show,id:exerciseInstance.id)
          }
          else {
              render(view:'edit',model:[exerciseInstance:exerciseInstance])
          }
      }
      else {
          flash.message = "Nie znaleziono ćwiczenia o ${params.id}"
          redirect(action:edit,id:params.id)
      }
  }

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
}
