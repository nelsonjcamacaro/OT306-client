package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.melvin.ongandroid.model.NosotrosActivities.MembersRemoteDataSource
import com.melvin.ongandroid.model.NosotrosActivities.MembersRepository

class MembersViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val membersRemoteDataSource = MembersRemoteDataSource()
        val repository = MembersRepository(membersRemoteDataSource)
        return MembersViewModel(repository) as T
    }
}