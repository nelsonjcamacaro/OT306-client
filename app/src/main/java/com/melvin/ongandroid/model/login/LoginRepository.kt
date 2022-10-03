package com.melvin.ongandroid.model.login

import com.melvin.ongandroid.model.news.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(private val apiService: ApiService) {
    suspend fun loginUser(email: String, password: String): LoginResponse =
        withContext(Dispatchers.IO) {
            apiService.getLoginUser(LoginModel(email,password))
        }
}