package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.*
import com.melvin.ongandroid.model.OngRemoteDataSource
import com.melvin.ongandroid.model.OngRepository
import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.launch

class TestimonialsViewModel(private val repository: OngRepository):ViewModel() {

    private val _testimonialsResultState = MutableLiveData<ResultState<Any>>()
    val testimonialsResultState : LiveData<ResultState<Any>> get() = _testimonialsResultState

    init {
        loadTestimonials()
    }

    private fun loadTestimonials(){
        viewModelScope.launch {
            repository.getTestimonialsList().collect(){ resultState ->
                _testimonialsResultState.value = resultState
            }
        }
    }
}

class TestimonialsViewModelFactory: ViewModelProvider.Factory {
    override fun <T:ViewModel> create(modelClass: Class<T>):T{
        val remoteDataSource = OngRemoteDataSource()
        val repository = OngRepository(remoteDataSource)

        return TestimonialsViewModel(repository) as T
    }
}