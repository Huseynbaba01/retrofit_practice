package com.example.retrofitbasics.retrofit

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @POST("users/{id}")
    fun getUser(@Path("id") id:Int): Call<Model>

    @POST("users/")
    fun getUsers(): Call<List<Model>>


    @POST("createUser/")
    fun createUser(@Body model:Model): Call<UserAoth>






}