package com.savitrisekar.tuneinapp.presentation

import com.savitrisekar.tuneinapp.domain.base.ResultData
import com.savitrisekar.tuneinapp.domain.model.MusicPlaylist
import com.savitrisekar.tuneinapp.domain.usecase.GetPlaylistMusicUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class MusicPlaylistPresenterTest {
    private lateinit var presenter: MusicPlaylistPresenter

    private val getPlaylistMusicUseCase: GetPlaylistMusicUseCase = mock()
    private val view: MusicPlaylistContract.View = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        presenter = MusicPlaylistPresenter(
            getPlaylistMusicUseCase = getPlaylistMusicUseCase
        )
        presenter.doAttachView(view)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch playlist result error`() = runTest {
        // Given
        val value = ResultData.Error(exception = RuntimeException())

        // Stub
        Mockito.`when`(getPlaylistMusicUseCase.invoke(any())).thenReturn(flow { emit(value) })

        // When
        presenter.getPlaylist("lany")

        // Then
        verify(view, Mockito.times(1)).onLoadingFetchPlaylist(true)
        verify(view, Mockito.times(1)).onLoadingFetchPlaylist(false)
        verify(view, Mockito.times(1)).onErrorFetchPlaylist(null)
        verify(view, Mockito.never()).onEmptyFetchPlaylist()
        verify(view, Mockito.never()).onErrorFetchPlaylist(any())
        verify(view, Mockito.never()).onSuccessFetchPlaylist(any())
    }

    @Test
    fun `fetch playlist result failure`() = runTest {
        // Given
        val value = ResultData.Failure(message = "something went wrong")

        // Stub
        Mockito.`when`(getPlaylistMusicUseCase.invoke(any())).thenReturn(flow { emit(value) })

        // When
        presenter.getPlaylist("lany")

        // Then
        verify(view, Mockito.times(1)).onLoadingFetchPlaylist(true)
        verify(view, Mockito.times(1)).onLoadingFetchPlaylist(false)
        verify(view, Mockito.never()).onErrorFetchPlaylist(null)
        verify(view, Mockito.never()).onEmptyFetchPlaylist()
        verify(view, Mockito.times(1)).onErrorFetchPlaylist(any())
        verify(view, Mockito.never()).onSuccessFetchPlaylist(any())
    }

    @Test
    fun `fetch playlist result success data empty`() = runTest {
        // Given
        val value = ResultData.Success(emptyList<MusicPlaylist>())

        // Stub
        Mockito.`when`(getPlaylistMusicUseCase.invoke(any())).thenReturn(flow { emit(value) })

        // When
        presenter.getPlaylist("lany")

        // Then
        verify(view, Mockito.times(1)).onLoadingFetchPlaylist(true)
        verify(view, Mockito.times(1)).onLoadingFetchPlaylist(false)
        verify(view, Mockito.never()).onErrorFetchPlaylist(null)
        verify(view, Mockito.times(1)).onEmptyFetchPlaylist()
        verify(view, Mockito.never()).onErrorFetchPlaylist(any())
        verify(view, Mockito.never()).onSuccessFetchPlaylist(any())
    }

    @Test
    fun `fetch playlist result success`() = runTest {
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
        Mockito.`when`(getPlaylistMusicUseCase.invoke(any())).thenReturn(flow { emit(value) })

        // When
        presenter.getPlaylist("lany")

        // Then
        verify(view, Mockito.times(1)).onLoadingFetchPlaylist(true)
        verify(view, Mockito.times(1)).onLoadingFetchPlaylist(false)
        verify(view, Mockito.never()).onErrorFetchPlaylist(null)
        verify(view, Mockito.never()).onEmptyFetchPlaylist()
        verify(view, Mockito.never()).onErrorFetchPlaylist(any())
        verify(view, Mockito.times(1)).onSuccessFetchPlaylist(any())
    }
}