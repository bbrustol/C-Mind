package com.bbrustol.cmindtest.data.source

import com.bbrustol.cmindtest.BuildConfig
import com.bbrustol.cmindtest.data.model.NewsModel
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET(BuildConfig.SOURCES)
    fun getSourceNews(@Query("apiKey")apiKey:String): Single<NewsModel>
}