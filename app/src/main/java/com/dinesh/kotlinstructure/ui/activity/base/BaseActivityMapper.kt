package com.dinesh.kotlinstructure.ui.activity.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.dinesh.kotlinstructure.util.network.NetworkUtils


class BaseActivityMapper {
    private var networkBroadcast: BroadcastReceiver? = null
    private var isRegister = false


    companion object {
        private var INSTANCE: BaseActivityMapper? = null
        fun get() = INSTANCE ?: synchronized(BaseActivityMapper::class) {
            BaseActivityMapper().also { INSTANCE = it }
        }
    }

    fun registerNetworkBroadcast(mContext: Context?, mListner: OnNetworkChange?) {
        if (isRegister)
            return
        networkBroadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                mListner?.networkUpdated(NetworkUtils.isNetworkConnected(context), NetworkUtils.networkType(context))
            }
        }
        mContext?.apply {
            isRegister = true
            registerReceiver(networkBroadcast!!, registerNetworkFilter())
        }
    }

    fun unregisterReceivers(mContext: Context?) {
        mContext?.apply {
            isRegister = false
            unregisterReceiver(networkBroadcast!!)
        }
    }

    fun freeResource(mContext: Context?) {
        unregisterReceivers(mContext)
        networkBroadcast = null
    }

    interface OnNetworkChange {
        fun networkUpdated(isConnected: Boolean, networkType: NetworkUtils.NetworkType)
    }


    private fun registerNetworkFilter(): IntentFilter {
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        return filter
    }


}