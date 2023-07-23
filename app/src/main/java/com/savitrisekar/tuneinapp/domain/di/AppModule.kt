package com.savitrisekar.tuneinapp.domain.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideLocationManager(): LocationManager {
        return application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ForApplication