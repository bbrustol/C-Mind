package com.bbrustol.cmindtest.data.repository

import com.bbrustol.cmindtest.data.model.NewsModel
import io.reactivex.Single

interface NewsRepository {
    fun getNews(ApiKey:String): Single<NewsModel>
}