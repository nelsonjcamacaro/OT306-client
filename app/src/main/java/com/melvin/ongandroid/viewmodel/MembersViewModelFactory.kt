package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.melvin.ongandroid.model.OngRemoteDataSource
import com.melvin.ongandroid.model.nosotrosActivities.MembersRepository

class MembersViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val ongRemoteDataSource = OngRemoteDataSource()
        val repository = MembersRepository(ongRemoteDataSource)
        return MembersViewModel(repository) as T
    }
}