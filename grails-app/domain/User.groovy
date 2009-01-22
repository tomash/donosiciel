import java.security.MessageDigest
import sun.misc.BASE64Encoder
import sun.misc.CharacterEncoder
import java.util.Random

class User
{
  Long id
  Long version
  String email
  String password
  String crypted_password
  String salt
  String toString() { "$email (#$id)" }
  Integer role = 0 // 0 - noone, 1-student, 2-professor, 16-admin
  
    
  static constraints =
  {
    email(email:true)
    crypted_password(blank:false, crypted_password:true)
    salt(blank:false, salt:true)
  }
  
  static encodeWithSalt(str, salt) 
  {
    MessageDigest md = MessageDigest.getInstance('SHA')
    md.update("${str}--${salt}".getBytes('UTF-8')) 
    return (new BASE64Encoder()).encode(md.digest()) 
  }
  
  static generateSalt() {
    Random rnd = new Random()
    def chars = []
    ['A'..'Z','a'..'z','0'..'9'].each{chars += it}
    def salt = (1..16).collect{ chars[rnd.nextInt(chars.size())] }.join()
    assert salt.size() == 16
    return salt
  }

  def register_magic()
  {
    salt = this.generateSalt();
    crypted_password = this.encodeWithSalt(password, salt)
    password = ""
    role = 0  //nice try ;)
  }
  
  boolean authenticate(with_password) 
  {
    String encoded = encodeWithSalt(with_password, salt)
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
