package com.bbrustol.cmindtest.business.interectors

import com.bbrustol.cmindtest.data.model.ArticlesUseCases
import com.bbrustol.cmindtest.data.model.ArticlesModel
import com.bbrustol.cmindtest.data.repository.ArticlesRepository
import io.reactivex.Single

class ArticlesInteractor(private val articlesRepository: ArticlesRepository) : ArticlesUseCases {
    override fun getArticles(sources: String, apiKey: String): Single<ArticlesModel> {
        return articlesRepository.getArticles(sources, apiKey)
    }
}