package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.model.InicioActivitys.*
import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.launch

class ActivityViewModel(private val repository: ActivityRepository): ViewModel() {
    private var _slides = MutableLiveData<ResultState<Any>>()
    val slides: LiveData<ResultState<Any>> get() = _slides

    init {
        viewModelScope.launch {
            load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            repository.getActivity().collect() { resultState ->
                _slides.value = resultState
            }
        }

    }
}
