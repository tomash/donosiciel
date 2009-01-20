class Participation {
  Date startDateTime
  float endMark = 0.0
  Exercise exercise
  User user // prowadzący!
  
  static belongsTo = [Exercise, User]
  static optionals = ["endMark"]
  static hasMany = [posts:Post, students:User]
}
