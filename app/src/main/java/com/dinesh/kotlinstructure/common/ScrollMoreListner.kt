package com.dinesh.kotlinstructure.common

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class ScrollMoreListner : RecyclerView.OnScrollListener() {

    private var visibleThreshold = 5
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    var isLoading: Boolean = false
    private var onLoadMoreListener: OnLoadMoreListener? = null
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (recyclerView.layoutManager is LinearLayoutManager) {

            val linearLayoutManager = recyclerView
                    .layoutManager as LinearLayoutManager

            totalItemCount = linearLayoutManager.itemCount
            lastVisibleItem = linearLayoutManager
                    .findLastVisibleItemPosition()
            if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                if (onLoadMoreListener != null) {
                    onLoadMoreListener!!.onLoadMore()
                }
                isLoading = true
            }
        }
    }

    fun setThreshold(threshold: Int) {
        this.visibleThreshold = threshold
    }

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }
}