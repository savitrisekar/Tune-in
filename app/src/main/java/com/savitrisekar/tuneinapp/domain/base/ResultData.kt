package com.savitrisekar.tuneinapp.domain.base

sealed class ResultData<out T> {
    class Success<out T : Any>(val data: T) : ResultData<T>()
    class Error(val exception: Exception) : ResultData<Nothing>()
    class Failure(val message: String) : ResultData<Nothing>()
}
