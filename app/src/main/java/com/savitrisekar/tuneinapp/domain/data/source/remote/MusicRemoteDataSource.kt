package com.savitrisekar.tuneinapp.domain.data.source.remote

import com.savitrisekar.tuneinapp.domain.base.DataResource
import com.savitrisekar.tuneinapp.domain.data.model.response.MusicPlaylistResponse

interface MusicRemoteDataSource {
    suspend fun fetchPlaylist(
        term: String,
        entity: String = "song"
    ): DataResource<List<MusicPlaylistResponse>>
}