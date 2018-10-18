package com.dinesh.kotlinstructure.models

import com.dinesh.kotlinstructure.common.genericType
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ResultModel(

        @field:SerializedName("per_facet")
        val perFacet: Any? = null,

        @field:SerializedName("org_facet")
        val orgFacet: Any? = null,

        @field:SerializedName("column")
        val column: Any? = null,

        @field:SerializedName("section")
        val section: String? = null,

        @field:SerializedName("abstract")
        val abstract: String? = null,

        @field:SerializedName("source")
        val source: String? = null,

        @field:SerializedName("asset_id")
        val assetId: Long? = null,

        @field:SerializedName("media")
        val media: List<MediaItem>? = null,

        @field:SerializedName("type")
        val type: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("des_facet")
        val desFacet: Any? = null,

        @field:SerializedName("url")
        val url: String? = null,

        @field:SerializedName("adx_keywords")
        val adxKeywords: String? = null,

        @field:SerializedName("geo_facet")
        val geoFacet: Any? = null,

        @field:SerializedName("id")
        val id: Long? = null,

        @field:SerializedName("byline")
        val byline: String? = null,

        @field:SerializedName("published_date")
        val publishedDate: String? = null,

        @field:SerializedName("views")
        val views: Int? = null
) {

    val gson = Gson()
    fun getDesFacet(): List<String> {
        return getData(desFacet)
    }

    fun getPerFacet(): List<String> {
        return getData(perFacet)
    }

    fun getOrgFacet(): List<String> {
        return getData(orgFacet)
    }

    fun getGeoFacet(): List<String> {
        return getData(geoFacet)
    }

    private fun getData(data: Any?): List<String> {
        if (data == null)
            return listOf()
        return when (data is String) {
            true -> {
                listOf()
            }
            false -> {
                gson.fromJson(perFacet.toString(), genericType<List<String>>())
            }
        }

    }

}