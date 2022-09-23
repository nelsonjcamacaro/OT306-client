package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.melvin.ongandroid.model.InicioActivitys.ActivityRemoteDataSource
import com.melvin.ongandroid.model.InicioActivitys.ActivityRepository

class ActivityViewModelFactory:ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val remoteDataSource = ActivityRemoteDataSource()
        val repository = ActivityRepository(remoteDataSource)
        return ActivityViewModel(repository) as T
    }
}