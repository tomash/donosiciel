class Participation {
  Date startDateTime
  float endMark
  Exercise exercise
  
  static belongsTo = Exercise
  static optionals = ["endMark"]
}
