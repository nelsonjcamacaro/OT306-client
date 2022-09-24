package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.melvin.ongandroid.model.Contact
import com.melvin.ongandroid.businesslogic.IsInputValidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val isInputValidUseCase : IsInputValidUseCase
) : ViewModel() {

    /*
     * Contact input information Live Data Instance
     */
    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact> get() = _contact

    /*
     * Subscribe Ui Send Message Button to this Boolean
     */
    private val _isValidInput = MutableLiveData<Boolean>()
    val isValidInput: LiveData<Boolean> get() = _isValidInput

    /*
     * Call this function on text change listener at name edit text
     */
    fun updateName(newName: String?) {
        _contact.value = Contact(
            name = newName,
            email = contact.value?.email,
            message = contact.value?.email
        )
        _isValidInput.value = isInputValidUseCase(contact.value)
    }

    /*
     * Call this function on text change listener at email edit text
     */
    fun updateEmail(newEmail: String?) {
        _contact.value = Contact(
            name = contact.value?.name,
            email = newEmail,
            message = contact.value?.email
        )
        _isValidInput.value = isInputValidUseCase(contact.value)
    }

    /*
     * Call this function on text change listener at message edit text
     */
    fun updateMessage(newMessage: String?) {
        _contact.value = Contact(
            name = contact.value?.name,
            email = contact.value?.email,
            message = newMessage
        )
        _isValidInput.value = isInputValidUseCase(contact.value)
    }

}