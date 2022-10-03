package com.melvin.ongandroid.model.login

data class LoginModel(
    val email: String = "",
    val password: String = ""
)

data class LoginToken(
    val token: String = ""
)

data class LoginResponse(
    val success: Boolean= false,
    val data: LoginToken?,
    val message: String = "",
    val error: String = ""
)
