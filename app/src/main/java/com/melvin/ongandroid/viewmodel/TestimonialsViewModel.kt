package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.model.NetWorkResponse
import com.melvin.ongandroid.model.OngRepository
import com.melvin.ongandroid.model.Testimonial
import com.melvin.ongandroid.model.TestimonialsResponse
import kotlinx.coroutines.launch

class TestimonialsViewModel(private val repository: OngRepository):ViewModel() {

    val testimonialsList = MutableLiveData<List<Testimonial>?>(null)


    fun loadTestimonials(){
        viewModelScope.launch {
            val result = repository.getTestimonialsList()
            testimonialsList.value = result
        }
    }
}