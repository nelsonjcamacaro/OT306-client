package com.melvin.ongandroid.model.nosotrosActivities

import com.melvin.ongandroid.model.OngRemoteDataSource
import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MembersRepository(private val ongRemoteDataSource: OngRemoteDataSource) {

    /*
     * Emit Result State as Loading while the connection is fetching data,
     * then emit Result State as Succes with members data or Result State as Error with message.
     */
    suspend fun getMembersResponse(): Flow<ResultState<Any>> = flow {
        emit(ResultState.Loading())
        emit(ongRemoteDataSource.getMembersResponse())
    }

}