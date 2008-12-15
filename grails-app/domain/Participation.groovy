class Participation {
  Date startDateTime
  float endMark
  static belongsTo = Exercise
  static optionals = ["endMark"]
}
