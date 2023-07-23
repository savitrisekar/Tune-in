package com.savitrisekar.tuneinapp.domain.base

import com.google.gson.annotations.SerializedName

sealed class ApiResponse<T>(
    @SerializedName("resultCount")
    val resultCount: Int?,
    @SerializedName("results")
    val results: T?
)