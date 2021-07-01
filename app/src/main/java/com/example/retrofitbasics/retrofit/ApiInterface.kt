package com.example.retrofitbasics.retrofit

import android.graphics.Picture
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*


interface ApiInterface {
    @POST("users/{id}")
    fun getUser(@Path("id") id:Int): Call<Model>

    @POST("users/")
    fun getUsers(): Call<List<Model>>


    /*@POST("createUser/")
    fun createUser(@Body model:Model): Call<UserAuth>*/
    @FormUrlEncoded
    @POST("createUser/")
    fun createUser(
        @Field("id", encoded = false) id:Int,
        @Field("login")login:String
    ): Call<UserAuth>

    @Multipart
    @POST("createImage/")
    fun sendFile(
//        @Part("some_data") requestBody: MultipartBody.Part
        @Part("some_data") picture: Picture
    ):Call<ResponseBody>












}