import java.security.MessageDigest
import sun.misc.BASE64Encoder
import sun.misc.CharacterEncoder

class User
{
  Long id
  Long version
  String email
  String password
  String crypted_password
  String salt
  String toString() { "$email" }
    
  static constraints =
  {
    email(email:true)
    password(blank:false, password:true)
  }
  
  static encodeWithSalt(str, salt) {
    MessageDigest md = MessageDigest.getInstance('SHA')
    md.update("${str}--${salt}".getBytes('UTF-8')) 
    return (new BASE64Encoder()).encode(md.digest()) 
  }
  
  boolean authenticate(password) {
    String encoded = encodeWithSalt(password, salt)
    if(encoded.equals(crypted_password))
      return true
    else
      return false
  }
}
