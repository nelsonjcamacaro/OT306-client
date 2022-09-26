package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.model.ContactMessageDto
import com.melvin.ongandroid.model.OngRepository
import kotlinx.coroutines.launch

class ContactViewModel (private val repository: OngRepository): ViewModel() {
    //live data usado para el envio de llamada POST
    var messageFromContact:MutableLiveData<ContactMessageDto> = MutableLiveData()
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
}


class ContactViewModelFactory(private val repository: OngRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(OngRepository::class.java).newInstance(repository)
    }
}