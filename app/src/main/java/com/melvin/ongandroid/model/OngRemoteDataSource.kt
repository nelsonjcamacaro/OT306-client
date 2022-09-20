package com.melvin.ongandroid.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


class OngRemoteDataSource {
    suspend fun getTestimonials(netWorkResponse: NetWorkResponse<List<Testimonial>>){

        return withContext(Dispatchers.IO){
                val response:Response<TestimonialsResponse> = RetrofitService
                    .instance
                    .create(GetTestimonialsService::class.java)
                    .getTestimonials()
                    response.body()?: emptyList<Testimonial>()
        }
    }
}


interface NetWorkResponse<T>{
    fun onResponse(value:T?)
}