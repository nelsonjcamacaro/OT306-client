package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.model.InicioActivitys.RepositoryError
import com.melvin.ongandroid.model.InicioActivitys.RepositoryResponse
import com.melvin.ongandroid.model.InicioActivitys.ResponseListener
import com.melvin.ongandroid.model.NosotrosActivities.Members
import com.melvin.ongandroid.model.NosotrosActivities.MembersList
import com.melvin.ongandroid.model.NosotrosActivities.MembersRepository
import kotlinx.coroutines.launch

class MembersViewModel(private val repository: MembersRepository): ViewModel(){
    val menbers = MutableLiveData<List<MembersList>>()
    init {
        viewModelScope.launch {
            load()
        }
    }

    private suspend fun load(){
        repository.getMembers(object : ResponseListener<Members>{
            override fun onError(repositoryError: RepositoryError) {

            }

            override fun onResponse(response: RepositoryResponse<Members>) {
                menbers.value = response.data.members
            }
        })
    }

}