package com.dinesh.kotlinstructure.util.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import com.dinesh.kotlinstructure.common.Consts

class NetworkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.run { LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(Consts.NETWORK_BR)) }
    }
}