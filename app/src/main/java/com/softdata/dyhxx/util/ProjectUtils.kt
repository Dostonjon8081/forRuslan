package com.softdata.dyhxx.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import android.util.Log
import java.io.BufferedWriter
import java.io.IOException
import java.util.prefs.Preferences



        fun getPref(activity: Activity): SharedPreferences{
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