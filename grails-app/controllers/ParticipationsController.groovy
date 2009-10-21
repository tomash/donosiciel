@Mixin(ApplicationController)
class ParticipationsController {
  
  def beforeInterceptor = [action:this.&require_teacher_logged_in,except: ['index','list','show']]

  def index = { redirect(action:list,params:params) }

  // the delete, save and update actions only accept POST requests
  static allowedMethods = [delete:'POST', save:'POST', update:'POST']

  def list = {
    require_logged_in()
    
    if(!params.max) params.max = 10
    def participations = Participation.executeQuery("SELECT p FROM Participation as p INNER JOIN p.students AS s WHERE s.id=" + session.user.id)
    //[ participationInstanceList: Participation.list( params ) ]
    [ participationInstanceList: participations ]
    
  }

  def show = {
    
      def participationInstance = Participation.get( params.id )
      
      if(!participationInstance.authorize(session.user))
      {
        authorization_denied()
        return false
      }
      
      def postsList = Post.findAll("from Post as p where p.participation=? order by createdAt DESC", [participationInstance])
      
      if(!participationInstance) {
          flash.message = "Nie znaleziono udziału o id ${params.id}"
          redirect(action:list)
      }
      else { return [ participationInstance : participationInstance, postsList : postsList ] }
  }

  def delete = {
      def participationInstance = Participation.get( params.id )
      if(participationInstance) {
          participationInstance.delete()
          flash.message = "Udział ${params.id} usunięty"
          redirect(action:list)
      }
      else {
          flash.message = "Nie znaleziono udziału o id ${params.id}"
          redirect(action:list)
      }
  }

  def edit = {
      def participationInstance = Participation.get( params.id )

      if(!participationInstance) {
          flash.message = "Nie znaleziono udziału o id ${params.id}"
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
              flash.message = "Udział ${params.id} zapisany"
              redirect(action:show,id:participationInstance.id)
          }
          else {
              render(view:'edit',model:[participationInstance:participationInstance])
          }
      }
      else {
          flash.message = "Nie znaleziono udziału o id ${params.id}"
          redirect(action:edit,id:params.id)
      }
  }

  def create = {
      def participationInstance = new Participation()
      
      participationInstance.properties = params
      participationInstance.exercise = Exercise.get( params.exercise_id )
      
      return ['participationInstance':participationInstance, 'zmienna':5]
  }

  def save = {
      def participationInstance = new Participation(params)
      if(!participationInstance.hasErrors() && participationInstance.save()) 
      {
          flash.message = "Udział ${participationInstance.id} został dodany"
          redirect(action:show,id:participationInstance.id)
      }
      else 
      {
          render(view:'create',model:[participationInstance:participationInstance])
      }
  }
  
  def add_student = {
    def participationInstance = Participation.get( params.id )
    
  }
  
  def save_student = {
    def participationInstance = Participation.get( params.id )
    def studentInstance = null
    if(params.student_id)
      studentInstance = User.get(params.student_id)
    else if(params.student_email)
      studentInstance = User.findByEmail(params.student_email.split(" ")[0])
    
    if(studentInstance)
    {
      participationInstance.addToStudents(studentInstance)
      flash.message = "Student ${studentInstance.email} został dodany"
    }
    redirect(action:show, id:participationInstance.id)
  }
  
  def ajaxAutoComplete = {
    if(params.student_email)
    {
      def input = params.student_email + '%'
      def name_input = '%' + params.student_email + '%'
      
      def list = User.findAll("from User as user where lower(user.email) like :eml OR lower(user.name) like :nm", [eml:input, nm:name_input])
      
      StringBuffer idList = new StringBuffer()
      idList.append("<ul>")
      //list?.each{em -> idList.append("<li>" + em.email + "</li>")}
      list?.each{em -> idList.append("<li>${em.email} (${em.name})</li>")}
      //list?.each{em -> idList.append("<li>" + em.toString + "</li>")}
      idList.append("</ul>")  
      
      render idList.toString()
    }
  }
  
}

