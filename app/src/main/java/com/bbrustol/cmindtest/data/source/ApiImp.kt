package com.bbrustol.cmindtest.data.source

import com.bbrustol.cmindtest.data.model.ArticlesModel
import com.bbrustol.cmindtest.data.model.NewsModel
import com.bbrustol.cmindtest.data.repository.ArticlesRepository
import com.bbrustol.cmindtest.data.repository.NewsRepository
import io.reactivex.Single

class NewsApiImp(private val api: Api) : NewsRepository {
    override fun getNews(apiKey: String): Single<NewsModel> = api.getSources(apiKey = apiKey)
}

class ArticlesApiImp(private val api: Api) : ArticlesRepository {
    override fun getArticles(sources: String, page: Int, apiKey: String): Single<ArticlesModel> = api.getEverything(sources = sources, page = page , apiKey = apiKey)
}