package com.dinesh.kotlinstructure.view.base

interface BaseView {

    fun showProgress()
    fun hideProgress()
    fun isTesting(): Boolean
    fun destroy()
}