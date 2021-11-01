package uz.fizmasoft.dyhxx

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application(){
    override fun onCreate() {
        super.onCreate()
             FirebaseCrashlytics.getInstance().log("Salom Boooy") // Force a crash
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}

