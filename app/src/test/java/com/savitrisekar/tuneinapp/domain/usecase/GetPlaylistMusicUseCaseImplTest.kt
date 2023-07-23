package com.savitrisekar.tuneinapp.domain.usecase

import com.savitrisekar.tuneinapp.domain.base.ResultData
import com.savitrisekar.tuneinapp.domain.model.MusicPlaylist
import com.savitrisekar.tuneinapp.domain.repository.MusicRepository
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class GetPlaylistMusicUseCaseImplTest {

    private lateinit var useCase: GetPlaylistMusicUseCase

    private val repository: MusicRepository = mock()

    @Before
    fun setUp() {
        useCase = GetPlaylistMusicUseCaseImpl(
            repository = repository
        )
    }

    @Test
    fun `invoke when fetchPlaylist success`() = runTest {
        // Given
        val item = listOf(
            MusicPlaylist(
                id = "id",
                artworkUrl100 = "pict",
                trackName = "song",
                artistName = "lany",
                previewUrl = "playUrl"
            )
        )
        val value = ResultData.Success(data = item)

        // Stub
        Mockito.`when`(repository.fetchPlaylist(any())).thenReturn(flow { emit(value) })

        // When
        useCase.invoke("lany").collectLatest { resultData ->
            // Then
            TestCase.assertTrue(resultData is ResultData.Success)
            TestCase.assertTrue((resultData as ResultData.Success).data == value.data)
        }
    }
}