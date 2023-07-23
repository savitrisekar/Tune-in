package com.savitrisekar.tunein

import android.app.Application

class MyApplication : Application() {

    val appComponent = DaggerApplicationComponent.create()
}