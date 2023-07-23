package com.savitrisekar.tunein.domain.di

import com.savitrisekar.tunein.domain.data.repository.MusicRepositoryImpl
import com.savitrisekar.tunein.domain.data.source.remote.MusicRemoteDataSource
import com.savitrisekar.tunein.domain.repository.MusicRepository
import com.savitrisekar.tunein.domain.usecase.GetPlaylistMusicUseCase
import com.savitrisekar.tunein.domain.usecase.GetPlaylistMusicUseCaseImpl
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