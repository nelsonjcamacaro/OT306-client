package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.*
import com.melvin.ongandroid.businesslogic.IsInputValidUseCase
import com.melvin.ongandroid.model.ContactMessageDto
import com.melvin.ongandroid.model.OngRepository
import kotlinx.coroutines.launch

class ContactViewModel (
    private val repository: OngRepository,
    private val isInputValidUseCase: IsInputValidUseCase
): ViewModel() {
    //live data usado para el envio de llamada POST
     var messageFromContact: MutableLiveData<ContactMessageDto> = MutableLiveData()
   //livedata usado para el progress bar
    var isLoading = MutableLiveData<Boolean>()

    fun pushPost(post: ContactMessageDto){
        viewModelScope.launch {
            isLoading.value = true
            val response = repository.SendContactMessage(post)
            messageFromContact.value = post

            if (response != null){
                isLoading.value = false
            }
        }
    }

    /*
     * Temporal contact input information
     */
    private val _contact = MutableLiveData<ContactMessageDto>()
    val contact: LiveData<ContactMessageDto> get() = _contact

    /*
     * Subscribe Ui Send Message Button to this Boolean
     */
    private val _isValidInput = MutableLiveData(false)
    val isValidInput: LiveData<Boolean> get() = _isValidInput

    /*
     * Call this function on text change listener at name edit text
     */
    fun updateName(newName: String?) {
        _contact.value = ContactMessageDto(
            nameAndLastName = newName,
            email = contact.value?.email,
            message = contact.value?.message
        )
        _isValidInput.value = isInputValidUseCase(contact.value)
    }

    /*
     * Call this function on text change listener at email edit text
     */
    fun updateEmail(newEmail: String?) {
        _contact.value = ContactMessageDto(
            nameAndLastName = contact.value?.nameAndLastName,
            email = newEmail,
            message = contact.value?.message
        )
        _isValidInput.value = isInputValidUseCase(contact.value)
    }

    /*
     * Call this function on text change listener at message edit text
     */
    fun updateMessage(newMessage: String?) {
        _contact.value = ContactMessageDto(
            nameAndLastName = contact.value?.nameAndLastName,
            email = contact.value?.email,
            message = newMessage
        )
        _isValidInput.value = isInputValidUseCase(contact.value)
    }

}

class ContactViewModelFactory(private val repository: OngRepository, private val inputValidUseCase: IsInputValidUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(OngRepository::class.java, IsInputValidUseCase::class.java).newInstance(repository,inputValidUseCase)
    }
}