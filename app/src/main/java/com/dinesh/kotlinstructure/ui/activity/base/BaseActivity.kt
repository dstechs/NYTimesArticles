package com.dinesh.kotlinstructure.ui.activity.base

import com.dinesh.kotlinstructure.util.Functions

/**
 * Created by DineshS
 */

abstract class BaseActivity : SupportActivity() {

    var TAG_BASE: String = BaseActivity::class.java.simpleName

    override fun onDestroy() {
        Functions.hideKeypad(this)
        super.onDestroy()
    }

}