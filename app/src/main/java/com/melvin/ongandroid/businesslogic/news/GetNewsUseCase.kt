package com.melvin.ongandroid.businesslogic.news

import com.melvin.ongandroid.model.news.NewsRepository
import com.melvin.ongandroid.model.news.NewsResponse

class GetNewsUseCase(private val repo: NewsRepository) {
    suspend fun execute(): NewsResponse {
        return repo.getNews()
    }
}