package com.melvin.ongandroid.model.news

sealed class NewsViewState {
    object Loading: NewsViewState()
    data class Content(val content: List<NewsModel>): NewsViewState()
    data class Error(val error: Exception): NewsViewState()
}