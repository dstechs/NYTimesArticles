package com.dinesh.kotlinstructure.util.network

import android.content.Context
import android.net.ConnectivityManager
import com.dinesh.kotlinstructure.R
import com.dinesh.kotlinstructure.util.Functions

/**
 * Created by DineshS
 */
class NetworkUtils {
    companion object {
        fun isNetworkConnected(context: Context?): Boolean {
            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            val state = networkInfo != null && networkInfo.isConnected
            if (!state)
                Functions.showToast(context.getString(R.string.msg_no_internet), context)
            return state
        }

        fun networkType(context: Context?): NetworkType {
            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return when (networkInfo != null && networkInfo.typeName.equals("wifi", ignoreCase = true)) {
                true -> NetworkType.WIFI
                false -> NetworkType.NETWORK
            }
        }
    }

    enum class NetworkType {
        WIFI, NETWORK
    }

}