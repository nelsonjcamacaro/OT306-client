package com.melvin.ongandroid.model

import android.util.Log
import com.melvin.ongandroid.model.nosotrosActivities.MembersService
import com.melvin.ongandroid.model.nosotrosActivities.model.MemberDto
import com.melvin.ongandroid.model.nosotrosActivities.model.MembersResponse
import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

const val TAG = "OngRemoteDataSource"

class OngRemoteDataSource {
    suspend fun getTestimonials(netWorkResponse: NetWorkResponse<List<Testimonial>>) =
        withContext(Dispatchers.IO) {
            try {
                val response: Response<TestimonialsResponse> = RetrofitService
                    .instance
                    .create(GetTestimonialsService::class.java)
                    .getTestimonials()
                response.body() ?: emptyList<Testimonial>()
            } catch (e: Exception) {
                ResultState.Error(Exception(e.message))
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
                if (service.isSuccessful) {
                    Log.d(TAG, "Fetch Members Successfully -> ${service.message()}")
                    Log.d(TAG, "Members List Size -> ${service.body()!!.members.size}")
                    ResultState.Success(service.body()!!.members)
                } else {
                    Log.e(TAG, "Server Error Message -> ${service.message()}")
                    ResultState.Error(Exception(service.message()))
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
                return ResultState.Error(Exception("May be you don´t have a connection to internet"))
            }
        } catch (e: java.lang.Exception) {
            Log.e(TAG, e.toString())
            return ResultState.Error(Exception("May be you don´t have a connection to internet"))
        }
    }

}


interface NetWorkResponse<T> {
    fun onResponse(value: T?)
}