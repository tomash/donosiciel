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
  Integer role = 0 // 0 - noone, 1-student, 2-professor, 16-admin
  
    
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
  
  String roleToString()
  {
    String rolestring = ""
    switch(role)
    {
      case 0:
        rolestring = "nikt"
        break
      case 1:
        rolestring = "student"
        break
      case 2:
        rolestring = "prowadzący"
        break
      case 16:
        rolestring = "admin"
        break
      default:
        rolestring = "???"
    }
    
    return rolestring
  }
}
