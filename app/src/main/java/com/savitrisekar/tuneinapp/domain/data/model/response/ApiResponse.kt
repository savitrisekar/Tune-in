package com.savitrisekar.tuneinapp.domain.data.model.response

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("resultCount") val resultCount: Int?, @SerializedName("results") val results: T?
)