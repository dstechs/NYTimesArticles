package com.dinesh.kotlinstructure.router.parser

import com.google.gson.annotations.SerializedName

class ResponseParser<T> {

    @SerializedName("results")
    var data: T? = null

    @SerializedName("copyright")
    var copyright = ""

    @SerializedName("status")
    var code = "Failure"

    @SerializedName("num_results")
    var results = 0

    @SerializedName("errors")
    var errors = listOf<String>()

    fun getStatus(): Boolean {
        return code.equals("ok", ignoreCase = true)
    }

    override fun toString(): String {
        return "Response Parser {data =" + data + ", copyright = " + copyright + ",status = " + getStatus() + "}"
    }
}