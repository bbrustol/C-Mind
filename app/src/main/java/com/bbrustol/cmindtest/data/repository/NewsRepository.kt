package com.bbrustol.cmindtest.data.repository

import com.bbrustol.cmindtest.data.model.NewsModel
import io.reactivex.Single

interface NewsRepository {
    fun getNews(apiKey:String): Single<NewsModel>
}