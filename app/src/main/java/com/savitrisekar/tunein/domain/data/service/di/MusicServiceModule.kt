package com.savitrisekar.tunein.domain.data.service.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.savitrisekar.tunein.BuildConfig
import com.savitrisekar.tunein.domain.data.service.MusicApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
object MusicServiceModule {

    private const val MUSIC = "MUSIC"

    @Provides
    @Singleton
    fun provideChuckerInterceptor(
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(chuckerInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    @Named(MUSIC)
    fun provideMusicService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.MUSIC_API_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideMusicApiService(
        @Named(MUSIC) retrofit: Retrofit
    ): MusicApiService {
        return retrofit.create(MusicApiService::class.java)
    }
}