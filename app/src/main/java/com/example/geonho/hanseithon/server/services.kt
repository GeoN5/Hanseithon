package com.example.geonho.hanseithon.server

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UserService{
    @POST("/login")//로그인
    fun login(@Body user: User) : Call<LoginResponse>

    @POST("/register")//회원가입
    @Multipart
    fun register(@Part("data")register: Register, @Part profile: MultipartBody.Part): Call<Response>

    @POST("/register")//기본사진 회원가입
    @Multipart
    fun basicregister(@Part("data")register:Register):Call<Response>

    @DELETE("/{id}")//계정삭제
    fun delete(@Path("id")id: String) : Call<Response>

    @PUT("/{id}")//정보 수정
    @Multipart
    fun modifyUser(@Path("id")id:String,@Part("data")modify: ModifyUser, @Part profile: MultipartBody.Part):Call<UserResponse>

    @GET("/User/{id}")//유저 정보 불러오기
    fun loadUser(@Path("id")id:String) : Call<UserData>

}