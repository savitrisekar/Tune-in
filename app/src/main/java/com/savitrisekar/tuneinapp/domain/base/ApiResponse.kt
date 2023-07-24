package com.savitrisekar.tuneinapp.domain.base

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("resultCount")
    val resultCount: Int?,
    @SerializedName("results")
    val results: T?
)