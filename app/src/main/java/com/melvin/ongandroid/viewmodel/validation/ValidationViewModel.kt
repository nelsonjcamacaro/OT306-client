package com.melvin.ongandroid.viewmodel.validation

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class ValidationViewModel() : ViewModel() {

    private val _name = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _repeatPassword = MutableStateFlow("")

    fun setName(name: String) {
        _name.value = name
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setRepeatPassword(repeatPassword: String) {
        _repeatPassword.value = repeatPassword
    }

    val isSubmitButtonRegister: Flow<Boolean> = combine(
        _name,
        _email,
        _password,
        _repeatPassword
    ) { name, email, password, repeatPassword ->
        val isNameCorrect = name.length > 3
        val isEmailCorrect = email.matches(Patterns.EMAIL_ADDRESS.toRegex())
        val isPasswordCorrect = password.length > 8
        val isPasswordMatch = repeatPassword == password
        return@combine isNameCorrect && isEmailCorrect && isPasswordCorrect && isPasswordMatch
    }
}