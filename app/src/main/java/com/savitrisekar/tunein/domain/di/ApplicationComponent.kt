package com.savitrisekar.tunein.domain.di

import com.savitrisekar.tunein.domain.data.service.di.MusicServiceModule
import com.savitrisekar.tunein.domain.data.di.MusicDataModule
import com.savitrisekar.tunein.presentation.MusicPlaylistActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MusicDomainModule::class, MusicDataModule::class, MusicServiceModule::class])
interface ApplicationComponent{

    fun inject(musicPlaylistActivity: MusicPlaylistActivity)
}