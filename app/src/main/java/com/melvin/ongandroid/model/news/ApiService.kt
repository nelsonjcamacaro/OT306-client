package com.melvin.ongandroid.model.news

import com.google.gson.GsonBuilder
import com.melvin.ongandroid.model.OngResponse
import com.melvin.ongandroid.model.login.LoginModel
import com.melvin.ongandroid.model.login.LoginResponse
import com.melvin.ongandroid.model.singup.SingUpDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("api/news")
    suspend fun getNews(): NewsResponse

    @POST("api/login")
    suspend fun getLoginUser(@Body login: LoginModel): LoginResponse

    @POST("api/register")
    suspend fun registerUser (@Body singUpDto: SingUpDto): OngResponse
}

object RetrofitClient {
    val webservice by lazy {
        Retrofit.Builder()
            .baseUrl("https://ongapi.alkemy.org/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiService::class.java)
    }
}