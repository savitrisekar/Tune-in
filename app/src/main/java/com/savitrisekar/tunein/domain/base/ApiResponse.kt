package com.savitrisekar.tunein.domain.base

import com.google.gson.annotations.SerializedName

sealed class ApiResponse<T>(
    @SerializedName("resultCount")
    val resultCount: Int?,
    @SerializedName("results")
    val results: T?
)