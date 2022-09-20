package com.melvin.ongandroid.model

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface GetTestimonialsService {

    @GET("/api/testimonials")
    suspend fun getTestimonials(): Response<TestimonialsResponse>
}