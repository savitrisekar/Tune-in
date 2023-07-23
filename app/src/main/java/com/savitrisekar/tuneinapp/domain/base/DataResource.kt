package com.savitrisekar.tuneinapp.domain.base

sealed class DataResource<out T> private constructor() {
    data class Success<T>(val data: T) : DataResource<T>()
    data class Error(val message: String, val exception: Exception? = null) :
        DataResource<Nothing>()

    object Empty : DataResource<Nothing>()
}