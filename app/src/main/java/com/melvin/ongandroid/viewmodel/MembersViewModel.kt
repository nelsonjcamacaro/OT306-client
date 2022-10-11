package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.*
import com.melvin.ongandroid.model.OngRemoteDataSource
import com.melvin.ongandroid.model.nosotrosActivities.MembersRepository
import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.launch

class MembersViewModel(private val repository: MembersRepository) : ViewModel() {

    private var _membersResultState = MutableLiveData<ResultState<Any>>()
    val membersResultState: LiveData<ResultState<Any>> get() = _membersResultState

    init {
        loadMembersResult()
    }

    /*
     * Load result state from Members Repository
     */
    fun loadMembersResult() {
        viewModelScope.launch {
            repository.getMembersResponse().collect() { resultState ->
                _membersResultState.value = resultState
            }
        }
    }

}

class MembersViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val ongRemoteDataSource = OngRemoteDataSource()
        val repository = MembersRepository(ongRemoteDataSource)
        return MembersViewModel(repository) as T
    }
}