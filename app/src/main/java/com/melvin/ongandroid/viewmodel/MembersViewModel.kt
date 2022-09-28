package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.model.nosotrosActivities.MembersRepository
import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.launch

class MembersViewModel(private val repository: MembersRepository) : ViewModel() {

    private var _membersResultState = MutableLiveData<ResultState<Any>>()
    val membersResultState: LiveData<ResultState<Any>> get() = _membersResultState

    init {
        loadMembersResult()
    }

    /*
     * Load result state from Members Repository
     */
    fun loadMembersResult() {
        viewModelScope.launch {
            repository.getMembersResponse().collect() { resultState ->
                _membersResultState.value = resultState
            }
        }
    }

}