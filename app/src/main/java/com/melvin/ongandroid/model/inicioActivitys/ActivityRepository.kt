package com.melvin.ongandroid.model.inicioActivitys

import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ActivityRepository(private val remoteDataSource: ActivityRemoteDataSource) {
    suspend fun getActivity(): Flow<ResultState<Any>> = flow {
        emit(ResultState.Loading())
        emit(remoteDataSource.getActivity())

    }


}