package com.erayucar.kefsubscription.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object InternetUtil {

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo: NetworkInfo? = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}