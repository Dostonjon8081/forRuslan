package uz.fizmasoft.dyhxx.helper.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import uz.fizmasoft.dyhxx.activity.MainActivity

//lateinit var activity: Activity

//fun Any.setActivity(mainActivity: Activity){
//    activity = mainActivity
//}

fun getPref(activityFrom: Activity ): SharedPreferences {
    val pref by lazy { activityFrom.getPreferences(Context.MODE_PRIVATE) }
    return pref
}

//fun getPrefActive(): SharedPreferences {
//    val pref by lazy { activity.getPreferences(Context.MODE_PRIVATE) }
//    return pref
//}

fun Any.loge(message: Any?) = message?.let {
    val messageText =
        "${this.javaClass.name}:${if (message is Throwable) message.message else message}"
    Log.e(
        "AppDebugError",
        messageText,
        if (message is Throwable) message else null
    )
}

fun Any.logd(message: Any?) = message?.let {
    val messageText =
        "${this.javaClass.name}:${if (message is Throwable) message.message else message}"
    Log.d(
        "AppDebug",
        messageText,
        if (message is Throwable) message else null
    )
}
