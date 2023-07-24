package com.savitrisekar.tuneinapp.domain.di

import com.savitrisekar.tuneinapp.domain.data.di.MusicDataModule
import com.savitrisekar.tuneinapp.domain.data.service.di.MusicServiceModule
import com.savitrisekar.tuneinapp.presentation.MusicPlaylistActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    DispatcherModule::class,
    MusicDomainModule::class,
    MusicDataModule::class,
    MusicServiceModule::class])
interface ApplicationComponent {
    fun inject(musicPlaylistActivity: MusicPlaylistActivity)
}