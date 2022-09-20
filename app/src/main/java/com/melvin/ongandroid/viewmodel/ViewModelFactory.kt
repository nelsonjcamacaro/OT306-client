package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.melvin.ongandroid.model.OngRemoteDataSource
import com.melvin.ongandroid.model.OngRepository

class ViewModelFactory:ViewModelProvider.Factory {
    override fun <T:ViewModel> create(modelClass: Class<T>):T{
        val remoteDataSource = OngRemoteDataSource()
        val repository = OngRepository(remoteDataSource)

        return TestimonialsViewModel(repository) as T
    }
}