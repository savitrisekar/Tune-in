package com.savitrisekar.tuneinapp.domain.usecase

import com.savitrisekar.tuneinapp.domain.base.ResultData
import com.savitrisekar.tuneinapp.domain.model.MusicPlaylist
import com.savitrisekar.tuneinapp.domain.repository.MusicRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSearchPlaylistMusicUseCaseImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: MusicRepository
) : GetSearchPlaylistMusicUseCase {

    override fun invoke(term: String): Flow<ResultData<List<MusicPlaylist>>> =
        flow {
            emitAll(repository.fetchPlaylist(term = term))
        }.flowOn(dispatcher)
}