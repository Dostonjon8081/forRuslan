package uz.fizmasoft.dyhxx.helper.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ir.alirezabdn.wp7progress.WP7ProgressBar

private var toast: Toast? = null
fun Fragment.carToast(context: Context, string: String) {
    if (toast != null) {
        toast?.cancel()
    }
    toast = Toast.makeText(context, string, Toast.LENGTH_SHORT)
    toast?.show()
}

fun Activity.carToast(context: Context, string: String) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
}

fun WP7ProgressBar.show() {
    this.isVisible = true
    this.showProgressBar()
}

fun WP7ProgressBar.hide() {
    this.hideProgressBar()
    this.isVisible = false
}

fun isOnline(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        isConnectedNewApi(context)
    } else {
        isConnectedOld(context)
    }
}

@Suppress("DEPRECATION")
private fun isConnectedOld(context: Context): Boolean {
    var isConnected = false // Initial Value
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) isConnected = true
    return isConnected
}

@RequiresApi(Build.VERSION_CODES.M)
private fun isConnectedNewApi(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
    return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

}

