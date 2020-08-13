package com.maxproj.purebbs.config

import android.app.Application
import com.facebook.stetho.Stetho

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Config.application = this

        Stetho.initializeWithDefaults(this)
    }
}