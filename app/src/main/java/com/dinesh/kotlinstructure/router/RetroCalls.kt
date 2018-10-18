package com.dinesh.kotlinstructure.router

import com.dinesh.kotlinstructure.models.ResultModel
import com.dinesh.kotlinstructure.router.parser.ResponseParser
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class RetroCalls {

    fun getDataFromServer(mRequestType: ApiConstant.REQUEST_TYPE, mSession: ApiConstant.SECTIONS, mDays: String, offset: String): Observable<ResponseParser<MutableList<ResultModel>>> {
        val client = RetroClient.getClient(timeOut = 10, timeUnit = TimeUnit.SECONDS).create(RetrofitApis::class.java)
        val queryMap = hashMapOf<String, String>()
        queryMap[ApiConstant.API_KEY] = ApiConstant.KEY
        queryMap[ApiConstant.API_OFFSET] = offset
        return client.getRecords(mRequestType.getRequestType(), mSession.getSection(), mDays, queryMap)
    }

    companion object {
        private var INSTANCE: RetroCalls? = null
        fun get() = INSTANCE ?: synchronized(this) {
            RetroCalls().also { INSTANCE = it }
        }
    }

}