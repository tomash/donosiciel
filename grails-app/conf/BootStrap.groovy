class BootStrap {

  def init = { servletContext ->
    new User(email:"eg@eg.com",password:"password", crypted_password:User.encodeWithSalt("password", "xxx"), salt:"xxx").save()
    new User(email:"user@eg.com",password:"password", crypted_password:User.encodeWithSalt("password", "yyy"), salt:"yyy").save()
    new User(email:"example@eg.com",password:"password", crypted_password:User.encodeWithSalt("password", "zzz"), salt:"zzz").save()
    println("BOOOOOOOOOOOOOOOOOTSTRAAAAAP!")
  }
  
  def destroy = {
  }

} 