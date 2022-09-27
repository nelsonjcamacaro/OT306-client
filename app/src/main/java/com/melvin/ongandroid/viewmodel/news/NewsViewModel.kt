package com.melvin.ongandroid.viewmodel.news

import androidx.lifecycle.*
import com.melvin.ongandroid.businesslogic.news.GetNewsUseCase
import com.melvin.ongandroid.model.news.NewsViewState
import kotlinx.coroutines.launch

class NewsViewModel(private val newsUseCase: GetNewsUseCase) : ViewModel() {
    private val _newsData = MutableLiveData<NewsViewState>()
    val newsData: LiveData<NewsViewState> get() = _newsData

    fun loadNews() {
        viewModelScope.launch {
            _newsData.postValue(NewsViewState.Loading)
            kotlin.runCatching {
                newsUseCase.execute()
            }.onSuccess {
                val data = newsUseCase.execute().data
                _newsData.postValue(NewsViewState.Content(data))
            }.onFailure {
                _newsData.postValue(NewsViewState.Error(Exception(it.message)))
            }
        }
    }
}

class NewsViewModelFactory(private val newsUseCase: GetNewsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetNewsUseCase::class.java).newInstance(newsUseCase)
    }
}