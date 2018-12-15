package com.bbrustol.cmindtest.data.source

import com.bbrustol.cmindtest.BuildConfig
import com.bbrustol.cmindtest.data.model.NewsModel
import com.bbrustol.cmindtest.data.repository.NewsRepository
import io.reactivex.Single

class NewsApiImp(private val newsApi: NewsApi) : NewsRepository {
    override fun getNews(apiKey: String): Single<NewsModel> = newsApi.getSourceNews(apiKey = BuildConfig.NEWS_API_KEY)
}