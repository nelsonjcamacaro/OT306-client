package com.melvin.ongandroid.model.InicioActivitys

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityService {
    @GET("/api/slides")
    fun getActivity(): Call<Slides>
}