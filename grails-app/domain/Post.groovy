class Post {
  Date createdAt
  String content
  User user
  Participation participation
  String filename
  
  static belongsTo = [Participation,User]
  
  static mapping = { content type:"text" }
  
  static optionals = ["filename"]
  
  static constraints = {
    content(minLength:20, blank:false)
    filename(nullable: true)
  }
  
  int compareTo(Object o)  { 
    log.debug "Post position with content ''${content}'' asked to compare my createdAt ${createdAt} with object: ${o} which has createdAt ${o.createdAt}" 
    this.createdAt.compareTo(((Post)o).createdAt) 
  } 
  
}
