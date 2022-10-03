package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.model.NetWorkResponse
import com.melvin.ongandroid.model.OngRepository
import com.melvin.ongandroid.model.Testimonial
import com.melvin.ongandroid.model.TestimonialsResponse
import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TestimonialsViewModel(private val repository: OngRepository):ViewModel() {

    private var _testimonialsList = MutableLiveData<ResultState<Any>>()
    val testimonialsList : LiveData<ResultState<Any>> get() = _testimonialsList

    init {
        loadTestimonials()
    }


    private fun loadTestimonials(){
        viewModelScope.launch {
            repository.getTestimonialsList().collect(){ resultState ->
                _testimonialsList.value = resultState

            }
        }
    }
}