package com.melvin.ongandroid.viewmodel.login

import android.content.Context
import androidx.lifecycle.*
import com.melvin.ongandroid.businesslogic.login.LoginUseCase
import com.melvin.ongandroid.model.login.LoginModel
import com.melvin.ongandroid.model.login.LoginResponse
import com.melvin.ongandroid.model.login.LoginViewState
import com.melvin.ongandroid.model.login.SharedPreferences
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _loginUser = MutableLiveData<LoginResponse>()
    val loginUser: LiveData<LoginResponse> get() = _loginUser

    private val _loginViewState = MutableLiveData<LoginViewState>()
    val loginViewState: LiveData<LoginViewState> get() = _loginViewState

    fun loginUser(email: String, password: String, context: Context) {
        viewModelScope.launch {
            _loginViewState.postValue(LoginViewState.Loading)
            val response = loginUseCase.execute(email, password)
            if (response.success) {
                val sharedPreferences = SharedPreferences(context)
                val token = response.data?.token ?: ""
                sharedPreferences.userToken(token)
                _loginViewState.postValue(LoginViewState.Content(LoginModel(email, password)))
            } else {
                _loginViewState.postValue(LoginViewState.Error(Exception()))
            }
        }
    }
}

class LoginViewModelFactory(
    private val loginUseCase: LoginUseCase,
    private val sharedPreferences: SharedPreferences,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(loginUseCase, sharedPreferences) as T
    }
}