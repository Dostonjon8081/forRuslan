package com.softdata.dyhxx.helper.util

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.carToast(context: Context, string: String){
    Toast.makeText(context,string, Toast.LENGTH_LONG).show()
}