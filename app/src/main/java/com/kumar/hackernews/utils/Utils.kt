package com.kumar.hackernews.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build

class Utils(val context: Context?) {


    fun isConnected(): Boolean {
        var result = false
        val cm: ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork) ?: return result
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                result = true
            }
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                result = true
            }
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                result = true
            }
        } else {
            val networkInfo: NetworkInfo? = cm.activeNetworkInfo
            if (networkInfo != null) {
                if (networkInfo.type == ConnectivityManager.TYPE_WIFI) result = true
            }
            if (networkInfo != null) {
                if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) result = true
            }
            if (networkInfo != null) {
                if (networkInfo.type == ConnectivityManager.TYPE_VPN) result = true
            }

        }
        return result
    }
}