package com.melvin.ongandroid.model.login

sealed class LoginViewState {
    object Loading: LoginViewState()
    data class Content(val user: LoginModel): LoginViewState()
    data class Error(val error: Exception): LoginViewState()
}