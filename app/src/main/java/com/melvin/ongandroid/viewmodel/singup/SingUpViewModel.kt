package com.melvin.ongandroid.viewmodel.singup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.businesslogic.signup.SingUpUseCase
import com.melvin.ongandroid.model.news.RetrofitClient
import com.melvin.ongandroid.model.singup.SingUpDto
import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.launch

class SingUpViewModel(
    private val singUpUseCase: SingUpUseCase
) : ViewModel() {

    private val _registerResultState = MutableLiveData<ResultState<Any>>()
    val registerResultState: LiveData<ResultState<Any>> get() = _registerResultState

    private val _registerInput = MutableLiveData<SingUpDto>()
    private val registerInput: LiveData<SingUpDto> get() = _registerInput

    private val _repeatPassword = MutableLiveData<String?>()
    val repeatPassword: LiveData<String?> get() = _repeatPassword

    /*
     * Send post request across singUpUseCase.
     * Call only after a local validation of the register input.
     * This function receive the result of the post request
     * and update register Result State Live Data instance.
     */
    fun registerUser() {
        viewModelScope.launch {
            if (registerInput.value != null) {
                singUpUseCase.execute(registerInput.value!!).collect { resultState ->
                    _registerResultState.value = resultState
                }
            }
        }
    }

    /*
     * Call this function on text change listener at tInputName
     * Update name register input at SingUpDto Live Data instance.
     */
    fun updateName(newName: String) {
        _registerInput.value = SingUpDto(
            name = newName,
            email = registerInput.value?.email,
            password = registerInput.value?.password
        )
    }

    /*
     * Call this function on text change listener at tInputEmail
     * Update email register input at SingUpDto Live Data instance.
     */
    fun updateEmail(newEmail: String?) {
        _registerInput.value = SingUpDto(
            name = registerInput.value?.name,
            email = newEmail,
            password = registerInput.value?.password
        )
    }

    /*
     * Call this function on text change listener at tInputPassword
     * Update password register input at SingUpDto Live Data instance.
     */
    fun updatePassword(newPassword: String?) {
        _registerInput.value = SingUpDto(
            name = registerInput.value?.name,
            email = registerInput.value?.email,
            password = newPassword
        )
    }

    /*
     * Call this function on text change listener at tInputRepeatPassword
     * Update repeat password register input at SingUpDto Live Data instance.
     */
    fun updateRepeatPassword(newRepeatPassword: String?) {
        _repeatPassword.value = newRepeatPassword
    }

}

class SingUpViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val singUpUseCase = SingUpUseCase(RetrofitClient.webservice)
        return SingUpViewModel(singUpUseCase) as T
    }
}