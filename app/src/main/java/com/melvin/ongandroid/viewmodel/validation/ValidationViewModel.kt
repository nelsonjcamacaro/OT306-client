package com.melvin.ongandroid.viewmodel.validation

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class ValidationViewModel() : ViewModel() {

    val nameData = MutableLiveData<String>()
    val emailData = MutableLiveData<String>()
    val passwordData = MutableLiveData<String>()
    val repeatPasswordData = MutableLiveData<String>()

    val isValidData = MediatorLiveData<Boolean>().apply {
        this.value = false
        addSource(nameData) { name->
            val email = emailData.value
            val password = passwordData.value
            val repPassword = repeatPasswordData.value
            this.value = validateForm(name, email, password, repPassword)
        }
        addSource(emailData) { email->
            val name = nameData.value
            val password = passwordData.value
            val repPassword = repeatPasswordData.value
            this.value = validateForm(name, email, password, repPassword)
        }
        addSource(passwordData) { password->
            val email = emailData.value
            val name = nameData.value
            val repPassword = repeatPasswordData.value
            this.value = validateForm(name, email, password, repPassword)
        }
        addSource(repeatPasswordData) { repeatPassw->
            val password = passwordData.value
            val email = emailData.value
            val name = nameData.value
            this.value = validateForm(name, email, password, repeatPassw)
        }
    }

    fun validateForm(name: String?, email: String?, password: String?, repPassword: String?): Boolean {
        val isValidName = name != null && name.isNotBlank() && name.length > 3
        val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
        val passwordIsValid = password != null && password.isNotBlank() && password.length >= 8
        val isValidRepeatPassword = repPassword == password
        return isValidName && isValidEmail && passwordIsValid && isValidRepeatPassword
    }
}