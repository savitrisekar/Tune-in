package com.savitrisekar.tuneinapp.domain.usecase

import com.savitrisekar.tuneinapp.domain.base.ResultData
import com.savitrisekar.tuneinapp.domain.model.MusicPlaylist
import kotlinx.coroutines.flow.Flow

interface GetPlaylistMusicUseCase {
    fun invoke(term: String): Flow<ResultData<List<MusicPlaylist>>>
}