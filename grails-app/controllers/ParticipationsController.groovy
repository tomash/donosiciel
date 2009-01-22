class ParticipationsController {
  
  def beforeInterceptor = [action:this.&checkUser,except: ['index','list','show']]

  def checkUser() {
    if(!session.user) 
    {
      // i.e. user not logged in
      session["original_request"] = params
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
  def allowedMethods = [delete:'POST', save:'POST', update:'POST']

  def list = {
      if(!params.max) params.max = 10
      [ participationInstanceList: Participation.list( params ) ]
  }

  def show = {
      def participationInstance = Participation.get( params.id )
      
      if(!participationInstance.authorize(session.user))
      {
        flash.message = "Próba nieautoryzowanego dostępu"
        redirect(uri:"/")
        return false
      }
      
      def postsList = Post.findAll("from Post as p where p.participation=? order by createdAt ASC", [participationInstance])
      
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
      studentInstance = User.findByEmail(params.student_email)
    
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
      
      def list = User.findAll("from User as user where lower(user.email) like :eml", [eml:input])
      
      StringBuffer idList = new StringBuffer()
      idList.append("<ul>")
      list?.each{em -> idList.append("<li>" + em.email+"</li>")}
      idList.append("</ul>")  
      
      render idList.toString()
    }
  }
  
}

