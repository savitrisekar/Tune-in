package com.savitrisekar.tunein.domain.data.di

import com.savitrisekar.tunein.domain.data.service.MusicApiService
import com.savitrisekar.tunein.domain.data.source.remote.MusicRemoteDataSource
import com.savitrisekar.tunein.domain.data.source.remote.MusicRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object MusicDataModule {

    @Provides
    @Singleton
    fun provideMusicRemoteDataSource(musicApiService: MusicApiService): MusicRemoteDataSource {
        return MusicRemoteDataSourceImpl(
            apiService = musicApiService
        )
    }
}