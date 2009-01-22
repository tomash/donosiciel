class Participation {
  Date startDateTime
  float endMark = 0.0
  Exercise exercise
  User user // prowadzący!
  
  static belongsTo = [Exercise, User]
  static optionals = ["endMark"]
  static hasMany = [posts:Post, students:User]
  
  boolean authorize(checked_user)
  {
    if(!checked_user) // się wylogował był
      return false
    
    if(checked_user.role >= 16) // admin
      return true
    if(user.id==checked_user.id)  // is the teacher/owner
      return true
    if(students.find{ it.id == checked_user.id})
      return true
    
    return false
  }
  
}
