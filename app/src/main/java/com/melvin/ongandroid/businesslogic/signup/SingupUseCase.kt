package com.melvin.ongandroid.businesslogic.signup

import com.melvin.ongandroid.model.news.ApiService
import com.melvin.ongandroid.model.singup.SingUpDto
import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

const val TAG = "SingUpUseCase"

class SingUpUseCase (private val apiService: ApiService) {

    /*
     * Call this function passing SingUpDto object after validation
     * from Sing Up View Model to register a new User in the Server.
     * This function emit the result of the transaction as ResultState instance.
     */
    suspend fun execute(post: SingUpDto): Flow<ResultState<Any>> = flow {
        emit(ResultState.Loading())
        try {
            val response = apiService.registerUser(post)
            try {
                if (response.success == true && response.data != null) {
                    emit(ResultState.Success(response.data))
                } else {
                    emit(ResultState.Error(Exception(response.errors?.email ?: "Unknown error")))
                }
            } catch (e: Exception) {
                emit(ResultState.Error(Exception("May be you don´t have a connection to internet")))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(Exception("The email has already been taken.")))
            //Todo replace the line above with the line below when de server is working fine
            //ResultState.Error(Exception(e.message))
        }
    }

}