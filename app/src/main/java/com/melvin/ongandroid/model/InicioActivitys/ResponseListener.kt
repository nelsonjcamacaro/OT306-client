package com.melvin.ongandroid.model.InicioActivitys

interface ResponseListener<T> {
    fun onResponse(response: RepositoryResponse<T>)

    fun onError(repositoryError: RepositoryError)
}