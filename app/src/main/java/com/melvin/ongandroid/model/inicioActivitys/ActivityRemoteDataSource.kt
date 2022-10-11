package com.melvin.ongandroid.model.inicioActivitys


import android.util.Log
import com.melvin.ongandroid.model.RetrofitService
import com.melvin.ongandroid.model.TAG
import com.melvin.ongandroid.utils.ResultState

class ActivityRemoteDataSource {
    suspend fun getActivity(): ResultState<Any> {
            val service = RetrofitService
                .instance
                .create(ActivityService::class.java)
                .getActivity()

        return try {
            if (service.isSuccessful && service.body()?.slides !=null){
                Log.d(TAG, "Fetch Testimonials Successfully -> ${service.message()}")
                Log.d(TAG, "Testimonials List Size -> ${service.body()!!.slides?.size}")
                ResultState.Success(service.body()!!.slides)
            }else{
                Log.e(TAG, "Server Error Message -> ${service.message()?: "Unknown error"}")
                ResultState.Error(Exception(service.message()?: "Unknown error\""))
            }
        }
        catch (e: Exception) {
            Log.e(TAG, e.toString())
            return ResultState.Error(Exception("May be you don´t have a connection to internet"))
        }

    }
}
