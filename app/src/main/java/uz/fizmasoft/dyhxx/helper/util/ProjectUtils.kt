package uz.fizmasoft.dyhxx.helper.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.util.Log


fun getPref(activity: Activity): SharedPreferences {
    val pref by lazy { activity.getPreferences(Context.MODE_PRIVATE) }
    return pref
}

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

fun Any.isOnline(context: Context): Boolean {
    val cm =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//    return cm.isActiveNetworkMetered
    val networkInfo = cm.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected

}