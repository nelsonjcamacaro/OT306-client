package com.melvin.ongandroid.model.login

import com.melvin.ongandroid.model.OngResponse
import com.melvin.ongandroid.model.news.ApiService
import com.melvin.ongandroid.model.singup.SingUpDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(private val apiService: ApiService) {
    suspend fun loginUser(email: String, password: String): LoginResponse =
        withContext(Dispatchers.IO) {
            apiService.getLoginUser(LoginModel(email,password))
        }

    suspend fun registerUser(post: SingUpDto): OngResponse =
        withContext(Dispatchers.IO) {
            apiService.registerUser(post)
        }
}