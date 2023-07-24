package com.savitrisekar.tuneinapp.domain.data.repository

import com.savitrisekar.tuneinapp.domain.base.DataResource
import com.savitrisekar.tuneinapp.domain.base.ResultData
import com.savitrisekar.tuneinapp.domain.data.source.remote.MusicRemoteDataSource
import com.savitrisekar.tuneinapp.domain.mapper.toMusicPlaylist
import com.savitrisekar.tuneinapp.domain.model.MusicPlaylist
import com.savitrisekar.tuneinapp.domain.repository.MusicRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val remote: MusicRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : MusicRepository {

    override fun fetchPlaylist(term: String): Flow<ResultData<List<MusicPlaylist>>> = flow {
        val result = when (val response = remote.fetchPlaylist(term = term)) {
            DataResource.Empty -> ResultData.Success(data = emptyList())
            is DataResource.Error -> response.exception?.let {
                ResultData.Error(it)
            } ?: run {
                ResultData.Failure(response.message)
            }

            is DataResource.Success -> ResultData.Success(
                data = response.data.map { it.toMusicPlaylist() }
            )
        }
        emit(result)
    }.flowOn(ioDispatcher)
}