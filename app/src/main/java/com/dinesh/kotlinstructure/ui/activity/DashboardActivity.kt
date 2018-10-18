package com.dinesh.kotlinstructure.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View
import com.dinesh.kotlinstructure.R
import com.dinesh.kotlinstructure.common.DataMapper
import com.dinesh.kotlinstructure.common.ScrollMoreListner
import com.dinesh.kotlinstructure.presentor.MainActivityPresentor
import com.dinesh.kotlinstructure.ui.activity.base.BaseActivityDelation
import com.dinesh.kotlinstructure.ui.adapter.ArticlesAdapter
import com.dinesh.kotlinstructure.util.Functions
import com.dinesh.kotlinstructure.view.MainActivityView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.empty_data.*


class DashboardActivity : BaseActivityDelation<MainActivityPresentor, MainActivityView>(), MainActivityView, ScrollMoreListner.OnLoadMoreListener {
    override fun isTesting(): Boolean {
        return false
    }

    private var rvAdapter: ArticlesAdapter? = null

    override fun showProgress() {
        if (DataMapper.mArticleDetails.isEmpty())
            srlContianer.isRefreshing = true
        llNoData.visibility = View.GONE
    }

    override fun hideProgress() {
        resetLoadMore()
        srlContianer.isRefreshing = false
    }

    override fun destroy() {

    }

    override fun setPresentor(): MainActivityPresentor? {
        return MainActivityPresentor()
    }

    override fun onAttach(): MainActivityView {
        return this
    }

    override fun freeResource() {

    }

    override fun setViewLayout(): Int {
        return R.layout.activity_dashboard
    }

    override fun onLayoutAdded(savedInstanceState: Bundle?) {
        super.onLayoutAdded(savedInstanceState)
        setToolbar()
        initViews()
        swipeListner()
        Functions.overflowIconColor(tbToolbar, Color.WHITE)
    }

    private fun setToolbar() {
        setSupportActionBar(tbToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.run { menuInflater.inflate(R.menu.dashboard_menu, menu) }
        return super.onCreateOptionsMenu(menu)
    }

    override fun canPlotData() {
        mPresentor?.requestData()
    }

    private fun initViews() {
        llNoData.visibility = View.GONE
        srlContianer.isRefreshing = false
        rvContent.layoutManager = LinearLayoutManager(this)
        rvContent.addOnScrollListener(getScrollingListner())
        rvAdapter = ArticlesAdapter()
        rvContent.adapter = rvAdapter
    }

    private var mScrollingListner: ScrollMoreListner? = null

    private fun getScrollingListner(): RecyclerView.OnScrollListener {
        if (mScrollingListner == null) {
            mScrollingListner = ScrollMoreListner()
            mScrollingListner!!.setOnLoadMoreListener(this)
            mScrollingListner!!.setThreshold(10)
        }
        return mScrollingListner!!
    }

    private fun swipeListner() {
        srlContianer.setOnRefreshListener {
            mPresentor!!.requestData()
        }
    }

    private fun resetLoadMore() {
        mScrollingListner?.takeIf { it.isLoading }?.apply {
            mScrollingListner!!.isLoading = false
            rvAdapter?.hideLoadProgress()
        }
    }

    override fun onLoadMore() {
        if (srlContianer.isRefreshing) {
            mScrollingListner?.isLoading = false
            return
        }
        rvAdapter?.showLoadProgress()
        mPresentor?.requestData(true)
    }


    override fun onDataAvailable() {
        rvAdapter?.run {
            if (itemCount > 0) {
                notifyItemInserted(DataMapper.mArticleDetails.size - 1)
            } else
                notifyItemRangeInserted(0, DataMapper.mArticleDetails.size - 1)
        }
    }

    override fun onDataNotFound() {
        if (DataMapper.mArticleDetails.isEmpty())
            llNoData.visibility = View.VISIBLE
    }

}
