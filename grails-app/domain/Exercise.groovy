class Exercise {
  String title
  String description
  Integer internal_number = 0
  
  static mapping = { description type:"text" }
  
  //static hasMany = [userexercises:UserExercise]
  static hasMany = [participations:Participation]
  
  static constraints = {
    title(minLength:10, blank:false)
    description(minLength:20, blank:false)
  }
  
  String toString() {"${this.id}: ${this.title}" }
}
