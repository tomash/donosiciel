class Exercise {
  String title
  String description
  //static hasMany = [userexercises:UserExercise]
  static hasMany = [participations:Participation]
  
  static constraints = {
    title(minLength:10, blank:false)
    description(minLength:20, blank:false)
  }
}
