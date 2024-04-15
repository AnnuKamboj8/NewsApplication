package com.example.new_application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        // initialize timber in application class
        Timber.plant(Timber.DebugTree())
    }
}