package com.bbrustol.cmindtest.data.source

import com.bbrustol.cmindtest.BuildConfig
import com.bbrustol.cmindtest.data.model.ArticlesModel
import com.bbrustol.cmindtest.data.model.NewsModel
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET(BuildConfig.SOURCES)
    fun getSources(@Query("apiKey")apiKey:String): Single<NewsModel>

    @GET(BuildConfig.EVERYTHING)
    fun getEverything(@Query("sources") sources: String, @Query("apiKey")apiKey:String): Single<ArticlesModel>

    @GET(BuildConfig.TOP_HEADLINES)
    fun getTopHeadlines(@Query("apiKey")apiKey:String): Single<ArticlesModel>
}