package com.savitrisekar.tunein.domain.repository

import com.savitrisekar.tunein.domain.base.ResultData
import com.savitrisekar.tunein.domain.model.MusicPlaylist
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    fun fetchPlaylist(term: String): Flow<ResultData<List<MusicPlaylist>>>
}