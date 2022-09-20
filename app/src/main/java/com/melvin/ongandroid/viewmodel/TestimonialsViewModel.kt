package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melvin.ongandroid.model.NetWorkResponse
import com.melvin.ongandroid.model.OngRepository
import com.melvin.ongandroid.model.Testimonial
import kotlinx.coroutines.launch

class TestimonialsViewModel(private val repository: OngRepository):ViewModel() {

    val testimonialsList = MutableLiveData<List<Testimonial>?>(null)

   init {
       viewModelScope.launch {
           loadTestimonials()

       }
    }

        private suspend fun loadTestimonials(){
        repository.getTestimonialsList(object : NetWorkResponse<List<Testimonial>>{
                override fun onResponse(value: List<Testimonial>?) {
                    testimonialsList.value=value

                }
            })
        }
}