package com.melvin.ongandroid.model.InicioActivitys


import com.melvin.ongandroid.model.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import retrofit2.Response

class ActivityRemoteDataSource {
    suspend fun getActivity(listener: ResponseListener<Slides>) {
        withContext(Dispatchers.IO){
            val service: Response<Slides> = RetrofitService.instance
                .create(ActivityService::class.java)
                .getActivity()
            service.body()?: emptyList<Slides>()

        }

    }
}