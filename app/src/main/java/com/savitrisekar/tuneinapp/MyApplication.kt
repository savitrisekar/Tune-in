package com.savitrisekar.tuneinapp

import android.app.Application

class MyApplication : Application() {

    val appComponent = DaggerApplicationComponent.create()
}