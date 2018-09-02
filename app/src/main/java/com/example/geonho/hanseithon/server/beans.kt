package com.example.geonho.hanseithon.server

data class Result(val success : Boolean,val message : String)

data class Response(val result: Result)

data class User(val id: String,val pw :String)

data class Register(val id:String,val pw: String,val name:String,val phone:String,val email:String)

data class Auth(val token:String)

data class Username(val id: String)

data class UserResponse(val result: Result,val user:Navigation)

data class Navigation(val name:String,val profile:String,val email: String)

data class LoginResponse(val result: Result, val auth: Auth, val user: Navigation)

data class UserData(val _id :String,val id:String,val pw:String,val profile:String,val name:String,val phone:String,val email:String)

data class ModifyUser(val pw:String,val phone:String,val email: String)

data class AllData(val id:String ,val title:String,val content:String,val img:String,val tag:String,val location:String,val createDate:String)

data class LostAll(val target:List<AllData>)

data class FindAll(val target:List<AllData>)

