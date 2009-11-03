@Mixin(ApplicationController)
class PostsController {
  
  def beforeInterceptor = [action:this.&checkUser, except: ['index','list','show']]

  def checkUser() {
    if(!session.user) 
    {
      // i.e. user not logged in
      session["original_request"] = params
      redirect(controller:'users',action:'login')
      return false
    }
    if(session.user.role < 1)
    {
      // i.e. user is not a student at least
      flash['message'] = "Próba nieautoryzowanego dostępu"
      redirect(uri:'/')
      return false
    }
  }

  def save = {
      
      def postInstance = new Post(params)      
      postInstance.user = session.user
      postInstance.createdAt = new Date()
    
      def f = request.getFile('uploaded_file')
      if(!f.empty) 
      {
        String timestamp = new Date().getTime().toString()
        String fname = session.user.id + '_' + timestamp + '_' + f.getOriginalFilename()
        //String fpath = grailsApplication.config.uploaded.location.toString() + postInstance.participation.id + File.separatorChar
        String fpath = grailsAttributes.getApplicationContext().getResource("/uploaded/"  + postInstance.participation.id + File.separatorChar).getFile().toString()
        new File(fpath).mkdirs()
        
        String path = fpath + File.separatorChar + fname
        
        postInstance.filename = fname
        f.transferTo(new File(path))								             			     	
      }
      /*
      else 
      {
        flash.message = 'file cannot be empty'
        redirect(uri:"/")
      }*/


      if(!postInstance.hasErrors() && postInstance.save()) {
          flash.message = "Wiadomość #${postInstance.id} dodana"
          redirect(controller:"participations",action:'show',id:postInstance.participation.id)
          
          def recipients = postInstance.participation.students.collect { it.email }
          recipients.add(postInstance.participation.user.email)
          recipients.add("tomekrs@o2.pl")
           
          sendMail {
            to recipients.toArray()
            subject "[Donosiciel] Nowa wiadomość w Twoim udziale"
            body g.render(template:"notification", model:[postInstance:postInstance])
          }
      }
      else {
          render(view:'create',model:[postInstance:postInstance])
      }
      //redirect(controller:"participations",action:'show',id:1)
  }

}
