package com.dinesh.kotlinstructure.presentor.base

open class BasePresentor<V> {
    var viewHandler: V? = null

    fun onAttach(mView: V) {
        this.viewHandler = mView
    }

    fun onDetach() {
        viewHandler = null
    }
}