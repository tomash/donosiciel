class ApplicationController {
  
  def require_logged_in() {
    if(!session.user) 
    {
      // i.e. user not logged in
      session["original_request"] = params
      flash['message'] = "Ta część serwisu wymaga zalogowania"
      redirect(controller:'users',action:'login')
      return false
    }
    return true
  }
  
  def require_teacher_logged_in() {
    if(require_logged_in()==false)
    { return false }
    
    if(session.user.role < 2)
    {
      // i.e. user is not a teacher or admin
      authorization_denied()
      return false
    }
    return true
  }
  
  def require_admin_logged_in() {
    
    if(require_logged_in()==false)
    { return false }
    
    if(session.user.role < 16)
    {
      // i.e. user is not an admin
      authorization_denied()
      return false
    }
    return true
  }
  
  def authorization_denied()
  {
    flash.message = "Próba nieautoryzowanego dostępu"
    session["original_request"] = params
    flash['message'] = "Próba nieautoryzowanego dostępu"
    redirect(controller:'users',action:'login')
  }

}
