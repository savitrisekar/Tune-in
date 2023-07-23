package com.savitrisekar.tunein.domain.data.repository

import com.savitrisekar.tunein.domain.base.DataResource
import com.savitrisekar.tunein.domain.base.ResultData
import com.savitrisekar.tunein.domain.data.source.remote.MusicRemoteDataSource
import com.savitrisekar.tunein.domain.mapper.toMusicPlaylist
import com.savitrisekar.tunein.domain.model.MusicPlaylist
import com.savitrisekar.tunein.domain.repository.MusicRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MusicRepositoryImpl(
    private val remote: MusicRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : MusicRepository {
    override fun fetchPlaylist(term: String): Flow<ResultData<List<MusicPlaylist>>> = flow {
        val result = when (val response = remote.fetchPlaylist(term)) {
            DataResource.Empty -> ResultData.Success(data = emptyList())
            is DataResource.Error -> response.exception?.let {
                ResultData.Error(it)
            }?: run {
                ResultData.Failure(response.message)
            }

            is DataResource.Success -> ResultData.Success(
                data = response.data.map { it.toMusicPlaylist() }
            )
        }
        emit(result)
    }.flowOn(ioDispatcher)
}