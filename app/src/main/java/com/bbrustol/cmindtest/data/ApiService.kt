package com.bbrustol.cmindtest.data

import com.bbrustol.cmindtest.BuildConfig
import com.bbrustol.cmindtest.data.model.ArticlesModel
import com.bbrustol.cmindtest.data.model.NewsModel
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(BuildConfig.SOURCES)
    fun getSources(@Query("apiKey")apiKey:String): Single<NewsModel>

    @GET(BuildConfig.EVERYTHING)
    fun getEverything(@Query("sources") sources: String, @Query("page")page: Int, @Query("apiKey")apiKey:String): Single<ArticlesModel>
}