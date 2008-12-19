class Post {
  Date createdAt
  String content
  User user
  Participation participation
  
  static belongsTo = [Participation,User]
  
  static mapping = { content type:"text" }
  
  static constraints = {
    content(minLength:20, blank:false)
  }
  
  int compareTo(Object o)  { 
    log.debug "Post position with content ''${content}'' asked to compare my createdAt ${createdAt} with object: ${o} which has createdAt ${o.createdAt}" 
    this.createdAt.compareTo(((Post)o).createdAt) 
  } 
}
