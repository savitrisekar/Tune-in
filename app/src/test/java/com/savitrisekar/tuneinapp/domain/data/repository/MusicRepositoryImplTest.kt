package com.savitrisekar.tuneinapp.domain.data.repository

import com.savitrisekar.tuneinapp.domain.base.DataResource
import com.savitrisekar.tuneinapp.domain.base.ResultData
import com.savitrisekar.tuneinapp.domain.data.model.response.MusicPlaylistResponse
import com.savitrisekar.tuneinapp.domain.data.source.remote.MusicRemoteDataSource
import com.savitrisekar.tuneinapp.domain.repository.MusicRepository
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class MusicRepositoryImplTest {

    private lateinit var repository: MusicRepository

    private val remoteDataSource: MusicRemoteDataSource = mock()
    private val coroutineDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        repository = MusicRepositoryImpl(
            ioDispatcher = coroutineDispatcher,
            remote = remoteDataSource
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch playlist when data return empty`() = runTest {
        // Given
        val result = DataResource.Empty

        // Stub
        Mockito.`when`(remoteDataSource.fetchPlaylist(any(), any())).thenReturn(result)

        // When
        repository.fetchPlaylist("lany").collectLatest { resultData ->
            // Then
            TestCase.assertTrue(resultData is ResultData.Success)
            TestCase.assertTrue((resultData as ResultData.Success).data.isEmpty())
        }
    }

    @Test
    fun `fetch playlist when data error null exception`() = runTest {
        // Given
        val messageError = "Error"
        val result = DataResource.Error(message = messageError, exception = null)

        // Stub
        Mockito.`when`(remoteDataSource.fetchPlaylist(any(), any())).thenReturn(result)

        // When
        repository.fetchPlaylist("lany").collectLatest { resultData ->
            // Then
            TestCase.assertTrue(resultData is ResultData.Failure)
            TestCase.assertEquals(messageError, (resultData as ResultData.Failure).message)
        }
    }

    @Test
    fun `fetch playlist when data error with exception`() = runTest {
        // Given
        val messageError = "Error"
        val result = DataResource.Error(message = messageError, exception = null)

        // Stub
        Mockito.`when`(remoteDataSource.fetchPlaylist(any(), any())).thenReturn(result)

        // When
        repository.fetchPlaylist("lany").collectLatest { resultData ->
            // Then
            TestCase.assertTrue(resultData is ResultData.Error)
            TestCase.assertEquals(messageError, (resultData as ResultData.Error).exception)
        }
    }

    @Test
    fun `fetch playlist when data success`() = runTest {
        // Given
        val response = listOf(
            MusicPlaylistResponse(
                trackId = "id",
                artworkUrl100 = "pict",
                trackName = "song",
                artistName = "lany",
                previewUrl = "playUrl",
                artistId = 1,
                artistViewUrl = "url",
                wrapperType = "url",
                kind = "",
                collectionId = 1,
                collectionName = "",
                collectionCensoredName = "",
                trackCensoredName = "",
                collectionArtistId = 1,
                collectionArtistViewUrl = "",
                collectionViewUrl = "",
                trackViewUrl = "",
                artworkUrl30 = "",
                artworkUrl60 = "",
                collectionPrice = 1.0,
                trackPrice = 1.0,
                trackRentalPrice = 1.0,
                collectionHdPrice = 1.0,
                trackHdPrice = 1.0,
                trackHdRentalPrice = 1.0,
                releaseDate = "",
                collectionExplicitness = "",
                trackExplicitness = "",
                discCount = 1,
                discNumber = 1,
                trackCount = 1,
                trackNumber = 1,
                trackTimeMillis = 1,
                country = "",
                currency = "",
                primaryGenreName = "",
                contentAdvisoryRating = "",
                shortDescription = "",
                longDescription = "",
                hasITunesExtras = false,
                isStreamable = false,
                collectionArtistName = ""
            )
        )
        val result = DataResource.Success(data = response)

        // Stub
        Mockito.`when`(remoteDataSource.fetchPlaylist(any(), any())).thenReturn(result)

        // When
        repository.fetchPlaylist("lany").collectLatest { resultData ->
            // Then
            TestCase.assertTrue(resultData is ResultData.Success)
            TestCase.assertTrue((resultData as ResultData.Success).data.isNotEmpty())
        }
    }
}