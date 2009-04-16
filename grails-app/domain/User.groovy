import java.security.MessageDigest
import sun.misc.BASE64Encoder
import sun.misc.CharacterEncoder
import java.util.Random

class User
{
  Long id
  Long version
  String names
  String email
  String password
  String crypted_password = ""
  String salt = ""
  //String toString() { "$email (#$id)" }
  String name = ""
  String toString() { "$name $email (#$id)" }
  Integer role = 0 // 0 - noone, 1-student, 2-professor, 16-admin
  
    
  static constraints =
  {
    email(email:true, blank:false, minSize:4, maxSize:20, unique:true)
    //password(size:5..15, blank:false)
    //crypted_password(blank:false)
    password(blank:false,password:true,minSize:6,maxSize:20)
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
    password = "crypted"
  }
  
  boolean authenticate(with_password) 
  {
    String encoded = encodeWithSalt(with_password, salt)
    if(encoded.equals(crypted_password))
      return true
    else
      return false
  }
  
  boolean authorize(checked_user)
  {
    if(!checked_user) // się wylogował był
      return false
    
    if(checked_user.role >= 16) // admin
      return true
    if(id==checked_user.id)  // is the owner
      return true

    //if all else fails...
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
