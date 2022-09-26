package com.melvin.ongandroid.utils

import java.lang.Exception

sealed class ResultState<out T>{
    class Loading <out T> :ResultState<T>()
    data class Success<out T>(val data: T):ResultState<T>()
    data class Error(val exception: Exception):ResultState<Nothing>()
}
