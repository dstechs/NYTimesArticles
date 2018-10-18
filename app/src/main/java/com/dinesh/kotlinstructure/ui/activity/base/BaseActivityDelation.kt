package com.dinesh.kotlinstructure.ui.activity.base

import android.os.Bundle
import com.dinesh.kotlinstructure.presentor.base.BasePresentor
import com.dinesh.kotlinstructure.util.Functions


/**
 * Created by DineshS
 */

abstract class BaseActivityDelation<P, V> : SupportActivity() where P : BasePresentor<V> {

    var TAG_BASE: String = BaseActivityDelation::class.java.simpleName

    var mPresentor: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresentor = setPresentor()
        mPresentor?.onAttach(onAttach())
    }

    abstract fun setPresentor(): P?

    abstract fun onAttach(): V

    override fun onDestroy() {
        Functions.hideKeypad(this)
        mPresentor?.onDetach()
        super.onDestroy()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
    }
}