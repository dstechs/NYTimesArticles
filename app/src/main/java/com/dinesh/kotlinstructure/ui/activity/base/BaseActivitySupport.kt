package com.dinesh.kotlinstructure.ui.activity.base

import android.os.Bundle

interface BaseActivitySupport {

    fun onNetworkChange(isConnected: Boolean)

    fun onLayoutAdded(savedInstanceState: Bundle?)

    fun canPlotData()

}