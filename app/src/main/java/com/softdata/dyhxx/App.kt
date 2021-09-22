package com.softdata.dyhxx

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.softdata.dyhxx.helper.util.LocaleHelper
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}

