package com.dinesh.kotlinstructure.router

import com.dinesh.kotlinstructure.models.ResultModel
import com.dinesh.kotlinstructure.router.parser.ResponseParser
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RetrofitApis {

    @GET("{requesttype}/{section}/{period}.json")
    fun getRecords(@Path("requesttype", encoded = true) requestType: String, @Path("section") section: String, @Path("period") period: String, @QueryMap query: HashMap<String, String>): Observable<ResponseParser<MutableList<ResultModel>>>

}