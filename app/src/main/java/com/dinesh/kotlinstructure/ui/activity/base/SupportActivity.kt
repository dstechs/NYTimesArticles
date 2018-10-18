package com.dinesh.kotlinstructure.ui.activity.base

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import com.dinesh.kotlinstructure.util.network.NetworkUtils

@SuppressWarnings("ALL")
abstract class SupportActivity : AppCompatActivity(), BaseActivitySupport {

    private val TAG: String = SupportActivity::class.java.simpleName

    private var isEntered: Boolean = false

    private val mControllerMapper: BaseActivityMapper by lazy { BaseActivityMapper.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFlags()
        if (validateLayout()) {
            setContentView(setViewLayout())
            onLayoutAdded(savedInstanceState)
        } else {
            Log.e(TAG, "Invalid View, please provide a valid view to continue with")
        }
    }

    private fun addFlags() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.e(TAG, "onPostCreate")
        savedInstanceState?.takeIf { it.getBoolean("isRecreate") }?.apply { runOnUiThread { canPlotData() } }
                ?: run {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)
                        runOnUiThread { canPlotData() }
                }
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        Log.e(TAG, "onEnterAnimationComplete")
        if (validateLayout() && !isEntered)
            canPlotData()

        isEntered = true
    }

    private fun validateLayout(): Boolean {
        return setViewLayout() > 0
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.e(TAG, "ondetachfromwindow")
        mControllerMapper.freeResource(this)
        freeResource()
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onstart")
        registerNetworkReceiver()
    }

    private fun registerNetworkReceiver() {
        mControllerMapper.registerNetworkBroadcast(this, object : BaseActivityMapper.OnNetworkChange {
            override fun networkUpdated(isConnected: Boolean, networkType: NetworkUtils.NetworkType) {
                onNetworkChange(isConnected)
            }
        })
    }

    fun getMapper(): BaseActivityMapper {
        return mControllerMapper
    }

    override fun onNetworkChange(isConnected: Boolean) {
    }

    override fun onLayoutAdded(savedInstanceState: Bundle?) {
    }

    override fun canPlotData() {
    }

    abstract fun freeResource()

    abstract fun setViewLayout(): Int
}