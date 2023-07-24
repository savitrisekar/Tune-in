package com.savitrisekar.tuneinapp.domain.data.service.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.savitrisekar.tuneinapp.BuildConfig
import com.savitrisekar.tuneinapp.domain.data.service.MusicApiService
import com.savitrisekar.tuneinapp.domain.di.ForApplication
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
object MusicServiceModule {

    private const val MUSIC = "MUSIC"

    @Provides
    fun provideChuckerInterceptor(@ForApplication context: Context): OkHttpClient {
        val chuckerInterceptor = ChuckerInterceptor
            .Builder(context)
            .apply {
                alwaysReadResponseBody(true)
                collector(ChuckerCollector(context, true))
            }
            .build()
        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(chuckerInterceptor)
        }.build()
    }

    @Provides
    @Named(MUSIC)
    fun provideMusicService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.MUSIC_API_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideMusicApiService(
        @Named(MUSIC) retrofit: Retrofit
    ): MusicApiService {
        return retrofit.create(MusicApiService::class.java)
    }
}