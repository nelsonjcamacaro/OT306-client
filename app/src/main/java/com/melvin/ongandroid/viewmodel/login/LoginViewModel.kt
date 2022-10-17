package com.melvin.ongandroid.viewmodel.login

import android.content.Context
import androidx.lifecycle.*
import com.melvin.ongandroid.businesslogic.login.LoginUseCase
import com.melvin.ongandroid.model.login.LoginModel
import com.melvin.ongandroid.model.login.LoginResponse
import com.melvin.ongandroid.model.login.LoginViewState
import com.melvin.ongandroid.model.login.SharedPreferences
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _loginUser = MutableLiveData<LoginResponse>()
    val loginUser: LiveData<LoginResponse> get() = _loginUser

    private val _loginViewState = MutableLiveData<LoginViewState>()
    val loginViewState: LiveData<LoginViewState> get() = _loginViewState

    private fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val specialCharacters = "-@%\\[\\}+'!/#$^?:;,\\(\"\\)~`.*=&\\{>\\]<_"
        val passwordRegex =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$specialCharacters])(?=\\S+$).{8,20}$"
        pattern = Pattern.compile(passwordRegex)
        val matcher: Matcher = pattern.matcher(password)
        return matcher.matches()
    }
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    fun validateLogin(password: String, email: String): Boolean {
        if (!(email.matches(emailPattern.toRegex())) || email.length < 4 || email.length > 25) {
            return false
        }
        if ((password.length < 8 || password.length >15) ||
            password.count { it.isDigit() } < 2 ||
            !password[0].isLowerCase() ||
            password.contains(password.lowercase()) ||
            !isValidPassword(password)
        ) {
            return false
        }
        return true
    }


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