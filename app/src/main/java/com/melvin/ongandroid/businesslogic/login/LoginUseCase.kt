package com.melvin.ongandroid.businesslogic.login

import com.melvin.ongandroid.model.login.LoginRepository
import com.melvin.ongandroid.model.login.LoginResponse

class LoginUseCase(private val repository: LoginRepository) {
    suspend fun execute(email: String, password: String): LoginResponse {
        return repository.loginUser(email, password)
    }
}