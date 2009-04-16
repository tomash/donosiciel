class BootStrap {

  def init = { servletContext ->
    
    if(!User.findByEmail("eg@example.com"))
      new User(email:"eg@example.com",password:"password", crypted_password:User.encodeWithSalt("password", "xxx"), salt:"xxx").save()
    
    if(!User.findByEmail("example@example.com"))
      new User(email:"example@example.com",password:"password", crypted_password:User.encodeWithSalt("password", "zzz"), salt:"zzz").save()
    
    if(!User.findByEmail("student1@example.com"))
      new User(email:"student1@example.com",password:"student", crypted_password:User.encodeWithSalt("student", "zzz"), salt:"zzz", role: 1).save()
    if(!User.findByEmail("student2@example.com"))
      new User(email:"student2@example.com",password:"student", crypted_password:User.encodeWithSalt("student", "zzz"), salt:"zzz", role: 1).save()
    if(!User.findByEmail("student3@example.com"))
      new User(email:"student3@example.com",password:"student", crypted_password:User.encodeWithSalt("student", "zzz"), salt:"zzz", role: 1).save()
    
    if(!User.findByEmail("teacher1@example.com"))
      new User(email:"teacher1@example.com",password:"teacher", crypted_password:User.encodeWithSalt("teacher", "zzz"), salt:"zzz", role: 2).save()
    if(!User.findByEmail("teacher2@example.com"))
      new User(email:"teacher2@example.com",password:"teacher", crypted_password:User.encodeWithSalt("teacher", "zzz"), salt:"zzz", role: 2).save()
    
    if(!User.findByEmail("admin@example.com"))
      new User(email:"admin@example.com",password:"admin", crypted_password:User.encodeWithSalt("admin", "zzz"), salt:"zzz", role: 16).save()
    
    println("Loaded bootstrapped users!")
  }
  
  def destroy = {
  }

} 