package com.savitrisekar.tuneinapp.domain.repository

import com.savitrisekar.tuneinapp.domain.base.ResultData
import com.savitrisekar.tuneinapp.domain.model.MusicPlaylist
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    fun fetchPlaylist(term: String): Flow<ResultData<List<MusicPlaylist>>>
}