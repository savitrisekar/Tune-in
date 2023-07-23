package com.savitrisekar.tunein.domain.data.source.remote

import com.savitrisekar.tunein.domain.base.DataResource
import com.savitrisekar.tunein.domain.data.model.response.MusicPlaylistResponse

interface MusicRemoteDataSource {
    suspend fun fetchPlaylist(
        term: String,
        entity: String = "song"
    ): DataResource<List<MusicPlaylistResponse>>
}