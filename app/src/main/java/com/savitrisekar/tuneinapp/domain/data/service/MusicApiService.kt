package com.savitrisekar.tuneinapp.domain.data.service

import com.savitrisekar.tuneinapp.domain.base.ApiResponse
import com.savitrisekar.tuneinapp.domain.data.model.response.MusicPlaylistResponse
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface MusicApiService {
    @GET("search")
    suspend fun fetchPlaylist(
        @Query("term") term: String,
        @Query("entity") entity: String = "song",
    ): ApiResponse<List<MusicPlaylistResponse>>
}