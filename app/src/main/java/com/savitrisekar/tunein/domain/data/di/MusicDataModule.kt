package com.savitrisekar.tunein.domain.data.di

import com.savitrisekar.tunein.domain.data.repository.MusicRepositoryImpl
import com.savitrisekar.tunein.domain.data.service.MusicApiService
import com.savitrisekar.tunein.domain.data.source.remote.MusicRemoteDataSource
import com.savitrisekar.tunein.domain.data.source.remote.MusicRemoteDataSourceImpl
import com.savitrisekar.tunein.domain.repository.MusicRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
object MusicDataModule {

    @Provides
    @Singleton
    fun provideMusicRemoteDataSource(musicApiService: MusicApiService): MusicRemoteDataSource {
        return MusicRemoteDataSourceImpl(
            musicApiService
        )
    }

    @Provides
    @Singleton
    fun provideMusicRepository(
        remote: MusicRemoteDataSource,
        ioDispatcher: CoroutineDispatcher
    ): MusicRepository {
        return MusicRepositoryImpl(
            remote,
            ioDispatcher
        )
    }
}