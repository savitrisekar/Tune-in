package com.savitrisekar.tunein.domain.data.source.remote

import com.savitrisekar.tunein.domain.base.DataResource
import com.savitrisekar.tunein.domain.data.model.response.MusicPlaylistResponse
import com.savitrisekar.tunein.domain.data.service.MusicApiService

class MusicRemoteDataSourceImpl(private val apiService: MusicApiService) : MusicRemoteDataSource {
    override suspend fun fetchPlaylist(
        term: String,
        entity: String
    ): DataResource<List<MusicPlaylistResponse>> {
        return try {
            val result = apiService.fetchPlaylist(term = term, entity = entity)
            when {

                result.results.isNullOrEmpty() -> {
                    DataResource.Empty
                }

                else -> {
                    DataResource.Success(data = result.results)
                }
            }
        } catch (e: Exception) {
            DataResource.Error(message = "error", exception = e)
        }
    }
}