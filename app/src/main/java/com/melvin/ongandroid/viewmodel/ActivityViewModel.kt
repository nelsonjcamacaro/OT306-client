package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.*
import com.melvin.ongandroid.model.inicioActivitys.*
import com.melvin.ongandroid.utils.ResultState
import kotlinx.coroutines.launch

class ActivityViewModel(private val repository: ActivityRepository): ViewModel() {
    private var _activitiesResultState = MutableLiveData<ResultState<Any>>()
    val activitiesResultState: LiveData<ResultState<Any>> get() = _activitiesResultState

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            repository.getActivity().collect() { resultState ->
                _activitiesResultState.value = resultState
            }
        }
    }
}

class ActivityViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val remoteDataSource = ActivityRemoteDataSource()
        val repository = ActivityRepository(remoteDataSource)
        return ActivityViewModel(repository) as T
    }
}