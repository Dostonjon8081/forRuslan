package uz.fizmasoft.dyhxx.helper.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

fun Fragment.carToast(context: Context, string: String){
    Toast.makeText(context,string, Toast.LENGTH_LONG).show()
}
fun Activity.carToast(context: Context, string: String){
    Toast.makeText(context,string, Toast.LENGTH_LONG).show()
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

