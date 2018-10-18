package com.dinesh.kotlinstructure.presentor

import com.dinesh.kotlinstructure.common.DataMapper
import com.dinesh.kotlinstructure.models.ResultModel
import com.dinesh.kotlinstructure.presentor.base.BasePresentor
import com.dinesh.kotlinstructure.router.ApiConstant
import com.dinesh.kotlinstructure.router.RetroCalls
import com.dinesh.kotlinstructure.router.parser.ResponseParser
import com.dinesh.kotlinstructure.view.MainActivityView
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivityPresentor : BasePresentor<MainActivityView>() {

    var offset: Int = 1
    fun requestData(isLoadMore: Boolean = false, requestType: ApiConstant.REQUEST_TYPE = ApiConstant.REQUEST_TYPE.MOST_POPULAR, sections: ApiConstant.SECTIONS = ApiConstant.SECTIONS.ALL_SECTION, mDays: String = "7") {
        if (!isLoadMore)
            offset = 1
        viewHandler?.showProgress()
        RetroCalls.get()
                .getDataFromServer(requestType, sections, mDays, (offset * 20).toString())
                .observeOn(getObserverOn())
                .subscribeOn(getSubscribeOn())
                .subscribe(object : DisposableObserver<ResponseParser<MutableList<ResultModel>>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: ResponseParser<MutableList<ResultModel>>) {
                        viewHandler?.hideProgress()
                        if (t.getStatus()) {
                            validateOffsetData(isLoadMore)
                            DataMapper.mArticleDetails.addAll(t.data as List<ResultModel>)
                            viewHandler?.onDataAvailable()
                        } else {
                            viewHandler?.onDataNotFound()
                        }
                    }

                    override fun onError(e: Throwable) {
                        viewHandler?.hideProgress()
                        viewHandler?.onDataNotFound()
                    }
                })

    }

    private fun getSubscribeOn(): Scheduler? {
        return viewHandler?.run { if (isTesting()) Schedulers.trampoline() else Schedulers.newThread() }
                ?: Schedulers.newThread()
    }

    private fun getObserverOn(): Scheduler? {
        return viewHandler?.run { if (isTesting()) Schedulers.trampoline() else AndroidSchedulers.mainThread() }
                ?: AndroidSchedulers.mainThread()
    }

    private fun validateOffsetData(isLoadMore: Boolean) {
        if (!isLoadMore)
            DataMapper.mArticleDetails.clear()
        else
            offset++
    }

}