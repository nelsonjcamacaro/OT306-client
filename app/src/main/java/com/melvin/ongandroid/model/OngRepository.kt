package com.melvin.ongandroid.model

import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OngRepository(private val remoteDataSource: OngRemoteDataSource) {

   suspend fun getTestimonialsList():Flow<ResultState<Any>> = flow {
        emit(ResultState.Loading())
        emit(remoteDataSource.getTestimonials())

   }

    suspend fun SendContactMessage(post: ContactMessageDto) {
        try {
            remoteDataSource.SendContactMessage(post)
        }catch (e:Exception){
            ResultState.Error(e)
        }
    }
}
