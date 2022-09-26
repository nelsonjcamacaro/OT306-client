package com.melvin.ongandroid.model.NosotrosActivities


import com.melvin.ongandroid.model.InicioActivitys.ResponseListener
import com.melvin.ongandroid.model.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MembersRemoteDataSource {
    suspend fun getMembers(listener: ResponseListener<Members>){
        withContext(Dispatchers.IO){
            val service: Response<Members> = RetrofitService.instance
                .create(MembersService::class.java)
                .getMembers()
                service.body()?: emptyList<Members>()

        }

    }
}