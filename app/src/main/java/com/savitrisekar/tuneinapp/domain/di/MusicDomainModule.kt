package com.savitrisekar.tuneinapp.domain.di

import com.savitrisekar.tuneinapp.domain.data.repository.MusicRepositoryImpl
import com.savitrisekar.tuneinapp.domain.data.source.remote.MusicRemoteDataSource
import com.savitrisekar.tuneinapp.domain.repository.MusicRepository
import com.savitrisekar.tuneinapp.domain.usecase.GetPlaylistMusicUseCase
import com.savitrisekar.tuneinapp.domain.usecase.GetPlaylistMusicUseCaseImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
object MusicDomainModule {
    @Provides
    @Singleton
    fun provideMusicRepository(
        remote: MusicRemoteDataSource,
        ioDispatcher: CoroutineDispatcher
    ): MusicRepository {
        return MusicRepositoryImpl(
            remote = remote,
            ioDispatcher = ioDispatcher
        )
    }

    @Provides
    @Singleton
    fun provideGetPlaylistMusicUseCase(
        repository: MusicRepository
    ): GetPlaylistMusicUseCase {
        return GetPlaylistMusicUseCaseImpl(repository = repository)
    }
}