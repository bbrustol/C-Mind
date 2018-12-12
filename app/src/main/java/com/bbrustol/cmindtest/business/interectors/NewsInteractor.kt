package com.bbrustol.cmindtest.business.interectors

import com.bbrustol.cmindtest.data.model.NewsUseCases
import com.bbrustol.cmindtest.data.model.NewsModel
import com.bbrustol.cmindtest.data.repository.NewsRepository
import io.reactivex.Single

class NewsInteractor(private val newsRepository: NewsRepository) : NewsUseCases {
    override fun getNews(apiKey: String): Single<NewsModel> {
        return newsRepository.getNews(apiKey)
    }
}