package com.melvin.ongandroid.model

import android.util.Log
import com.melvin.ongandroid.model.nosotrosActivities.MembersService
import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.awaitResponse

const val TAG = "OngRemoteDataSource"

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

    suspend fun SendContactMessage(post: ContactMessageDto) = withContext(Dispatchers.IO) {
        try {
            val response: Response<ContactMessageDto> = RetrofitService
                .instance
                .create(PostAPIService::class.java)
                .sendContactMessageToAPI(post)

        } catch (e: Exception) {
            ResultState.Error(Exception(e.message))
        }
    }

    /*
     * Fetch Members Data from remote Source
     */
    suspend fun getMembersResponse(): ResultState<Any> {
        try {
            val service = RetrofitService.instance
                .create(MembersService::class.java)
                .getMembers()
            return try {
                if (service.isSuccessful && service.body()?.data != null) {
                    Log.d(TAG, "Fetch Members Successfully -> ${service.message()}")
                    Log.d(TAG, "Members List Size -> ${service.body()!!.data?.size}")
                    ResultState.Success(service.body()!!.data!!)
                } else {
                    Log.e(TAG, "Server Error Message -> ${service.message()?: "Unknown error"}")
                    ResultState.Error(Exception(service.message()?: "Unknown error\""))
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
                return ResultState.Error(Exception("May be you don´t have a connection to internet"))
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            return ResultState.Error(Exception("May be you don´t have a connection to internet"))
        }
    }

}


interface NetWorkResponse<T> {
    fun onResponse(value: T?)
}