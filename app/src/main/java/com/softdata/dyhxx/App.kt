package com.softdata.dyhxx

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate

class App: Application() {
    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate()
        appContext = this
        appResources = resources
    }

    companion object {
        lateinit var appContext: Context
        lateinit var appResources: Resources
    }
}