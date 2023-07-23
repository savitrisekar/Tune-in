package com.savitrisekar.tunein.domain.usecase

import com.savitrisekar.tunein.domain.base.ResultData
import com.savitrisekar.tunein.domain.model.MusicPlaylist
import com.savitrisekar.tunein.domain.repository.MusicRepository
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