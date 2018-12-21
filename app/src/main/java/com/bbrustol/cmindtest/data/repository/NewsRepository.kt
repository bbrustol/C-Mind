package com.bbrustol.cmindtest.data.repository

import com.bbrustol.cmindtest.data.ApiService
import com.bbrustol.cmindtest.data.model.NewsModel
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val apiService: ApiService) {
    fun getNews(apiKey:String): Single<NewsModel> = apiService.getSources(apiKey)
}