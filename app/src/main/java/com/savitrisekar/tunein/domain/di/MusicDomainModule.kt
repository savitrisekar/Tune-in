package com.savitrisekar.tunein.domain.di

import com.savitrisekar.tunein.domain.repository.MusicRepository
import com.savitrisekar.tunein.domain.usecase.GetPlaylistMusicUseCase
import com.savitrisekar.tunein.domain.usecase.GetPlaylistMusicUseCaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object MusicDomainModule {

    @Provides
    @Singleton
    fun provideGetPlaylistMusicUseCase(
        repository: MusicRepository
    ): GetPlaylistMusicUseCase {
        return GetPlaylistMusicUseCaseImpl(repository)
    }
}