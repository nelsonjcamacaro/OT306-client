package com.melvin.ongandroid.model

import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.awaitResponse


class OngRemoteDataSource {
    suspend fun getTestimonials() :List<Testimonial>?{

        val service = RetrofitService
            .instance
            .create(GetTestimonialsService::class.java)
            .getTestimonials()
        return try {
            val response: Response<List<Testimonial>> = service.awaitResponse()
            response.body()
        } catch (e: Exception) {
            ResultState.Error(Exception(e.message))
            null
        }
    }

    suspend fun SendContactMessage(post: ContactMessageDto) = withContext(Dispatchers.IO){
        try {
            val response: Response<ContactMessageDto> = RetrofitService
                .instance
                .create(PostAPIService::class.java)
                .sendContactMessageToAPI(post)

        }catch (e:Exception){
            ResultState.Error(Exception(e.message))
        }
    }
}


interface NetWorkResponse<T>{
    fun onResponse(value:T?)
}