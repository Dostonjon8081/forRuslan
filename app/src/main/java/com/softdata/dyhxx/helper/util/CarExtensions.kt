package com.softdata.dyhxx.helper.util

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.carToast(context: Context, string: String){
    Toast.makeText(context,string, Toast.LENGTH_LONG).show()
}
fun Activity.carToast(context: Context, string: String){
    Toast.makeText(context,string, Toast.LENGTH_LONG).show()
}