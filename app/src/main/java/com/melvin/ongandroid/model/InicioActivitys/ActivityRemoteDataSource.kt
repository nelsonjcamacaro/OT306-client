package com.melvin.ongandroid.model.InicioActivitys

import com.melvin.ongandroid.BuildConfig
import com.melvin.ongandroid.model.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityRemoteDataSource {
    fun getActivity(listener: ResponseListener<Slides>){
        val service = RetrofitService.instance
            .create(ActivityService::class.java)
            .getActivity()

        service.enqueue(object : Callback<Slides>{

            override fun onResponse(call: Call<Slides>, response: Response<Slides>) {
                val slides = response.body()

                if (response.isSuccessful && slides != null){
                    listener.onResponse(
                        RepositoryResponse(
                        data = slides,
                        source = Source.REMOTE
                        )

                    )

                }else{
                    listener.onError(
                        RepositoryError(
                        message = "Error el servidor rechazo la solicitud",
                        code = response.code(),
                        source = Source.REMOTE
                        )
                    )
                }

            }

            override fun onFailure(call: Call<Slides>, t: Throwable) {
                listener.onError(
                    RepositoryError(
                        message = t.message ?: "Error inesperado",
                        code = -1,
                        source = Source.REMOTE
                    )
                )
            }

        })

    }
}