package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.model.nosotrosActivities.MembersRepository
import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.launch

class MembersViewModel(private val repository: MembersRepository) : ViewModel() {

    private var _membersResponse = MutableLiveData<ResultState<Any>>()
    val membersResponse: LiveData<ResultState<Any>> get() = _membersResponse

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            repository.getMembersResponse().collect() { resultState ->
                _membersResponse.value = resultState
            }
        }
    }

}