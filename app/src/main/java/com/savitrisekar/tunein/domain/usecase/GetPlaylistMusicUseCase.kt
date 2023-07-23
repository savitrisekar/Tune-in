package com.savitrisekar.tunein.domain.usecase

import com.savitrisekar.tunein.domain.base.ResultData
import com.savitrisekar.tunein.domain.model.MusicPlaylist
import kotlinx.coroutines.flow.Flow

interface GetPlaylistMusicUseCase {
    fun invoke(term: String): Flow<ResultData<List<MusicPlaylist>>>
}