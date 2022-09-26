package com.melvin.ongandroid.model

import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


class OngRemoteDataSource {
    suspend fun getTestimonials(netWorkResponse: NetWorkResponse<List<Testimonial>>) = withContext(Dispatchers.IO){
                try{
                    val response:Response<TestimonialsResponse> = RetrofitService
                        .instance
                        .create(GetTestimonialsService::class.java)
                        .getTestimonials()
                    response.body()?: emptyList<Testimonial>()
                } catch (e:Exception){
                    ResultState.Error(Exception(e.message))
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