package com.melvin.ongandroid.model.nosotrosActivities

import com.melvin.ongandroid.model.OngRemoteDataSource
import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MembersRepository(private val ongRemoteDataSource: OngRemoteDataSource) {

    suspend fun getMembersResponse(): Flow<ResultState<Any>> = flow {
        emit(ResultState.Loading())
        emit(ongRemoteDataSource.getMembersResponse())
    }

}