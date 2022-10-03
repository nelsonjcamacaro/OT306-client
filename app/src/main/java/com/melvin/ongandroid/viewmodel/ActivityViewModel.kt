package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.model.InicioActivitys.*
import kotlinx.coroutines.launch

class ActivityViewModel(private val repository: ActivityRepository): ViewModel() {
    val slides = MutableLiveData<List<Activity>>(listOf())
    init {
        viewModelScope.launch {
            load()
        }
    }

        suspend fun load(){
        repository.getActivity(object : ResponseListener<Slides> {
            override fun onResponse(response : RepositoryResponse<Slides>) {
                slides.value = response.data.slides

            }

            override fun onError(repositoryError: RepositoryError) {

            }
        })
    }
}