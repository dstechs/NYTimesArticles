package com.dinesh.kotlinstructure.view

import com.dinesh.kotlinstructure.view.base.BaseView

interface MainActivityView : BaseView {
    fun onDataAvailable()
    fun onDataNotFound()
}