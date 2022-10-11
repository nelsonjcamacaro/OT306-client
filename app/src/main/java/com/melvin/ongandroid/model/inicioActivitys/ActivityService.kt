package com.melvin.ongandroid.model.inicioActivitys

import retrofit2.Response
import retrofit2.http.GET

interface ActivityService {
    @GET("/api/slides")
   suspend fun getActivity(): Response<Slides>
}