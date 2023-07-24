package com.savitrisekar.tuneinapp.domain.di

import com.savitrisekar.tuneinapp.domain.data.repository.MusicRepositoryImpl
import com.savitrisekar.tuneinapp.domain.data.source.remote.MusicRemoteDataSource
import com.savitrisekar.tuneinapp.domain.repository.MusicRepository
import com.savitrisekar.tuneinapp.domain.usecase.GetPlaylistMusicUseCase
import com.savitrisekar.tuneinapp.domain.usecase.GetPlaylistMusicUseCaseImpl
import com.savitrisekar.tuneinapp.domain.usecase.GetSearchPlaylistMusicUseCase
import com.savitrisekar.tuneinapp.domain.usecase.GetSearchPlaylistMusicUseCaseImpl
import com.savitrisekar.tuneinapp.presentation.MusicPlaylistPresenter
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
class MusicDomainModule {

    @Provides
    @Singleton
    fun provideMusicRepository(
        remote: MusicRemoteDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): MusicRepository {
        return MusicRepositoryImpl(
            remote = remote,
            ioDispatcher = ioDispatcher
        )
    }

    @Provides
    @Singleton
    fun provideGetPlaylistMusicUseCase(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        repository: MusicRepository
    ): GetPlaylistMusicUseCase {
        return GetPlaylistMusicUseCaseImpl(dispatcher = ioDispatcher, repository = repository)
    }

    @Provides
    @Singleton
    fun provideGetSearchPlaylistMusicUseCase(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        repository: MusicRepository
    ): GetSearchPlaylistMusicUseCase {
        return GetSearchPlaylistMusicUseCaseImpl(dispatcher = ioDispatcher, repository = repository)
    }

    @Provides
    @Singleton
    fun provideMusicPlaylistPresenter(
        useCase: GetPlaylistMusicUseCase,
        getSearchPlaylistMusicUseCase: GetSearchPlaylistMusicUseCase
    ): MusicPlaylistPresenter {
        return MusicPlaylistPresenter(useCase, getSearchPlaylistMusicUseCase)
    }

}