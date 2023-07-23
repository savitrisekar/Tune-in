package com.savitrisekar.tuneinapp.domain.usecase

import com.savitrisekar.tuneinapp.domain.base.ResultData
import com.savitrisekar.tuneinapp.domain.model.MusicPlaylist
import com.savitrisekar.tuneinapp.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetPlaylistMusicUseCaseImpl constructor(
    private val repository: MusicRepository
) : GetPlaylistMusicUseCase {
    override fun invoke(term: String): Flow<ResultData<List<MusicPlaylist>>> =
        flow {
            emitAll(repository.fetchPlaylist(term = term))
        }
}