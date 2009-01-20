class BootStrap {

  def init = { servletContext ->
    new User(email:"eg@eg.com",password:"password", crypted_password:User.encodeWithSalt("password", "xxx"), salt:"xxx").save()
    new User(email:"user@eg.com",password:"password", crypted_password:User.encodeWithSalt("password", "yyy"), salt:"yyy").save()
    new User(email:"example@eg.com",password:"password", crypted_password:User.encodeWithSalt("password", "zzz"), salt:"zzz").save()
    new User(email:"student@eg.com",password:"student", crypted_password:User.encodeWithSalt("student", "zzz"), salt:"zzz", role: 1).save()
    new User(email:"teacher@eg.com",password:"teacher", crypted_password:User.encodeWithSalt("teacher", "zzz"), salt:"zzz", role: 2).save()
    new User(email:"admin@eg.com",password:"admin", crypted_password:User.encodeWithSalt("admin", "zzz"), salt:"zzz", role: 16).save()
    println("Loaded bootstrapped users!")
  }
  
  def destroy = {
  }

} 