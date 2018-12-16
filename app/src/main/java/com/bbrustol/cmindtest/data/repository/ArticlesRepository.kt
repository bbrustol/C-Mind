package com.bbrustol.cmindtest.data.repository

import com.bbrustol.cmindtest.data.model.ArticlesModel
import io.reactivex.Single

interface ArticlesRepository {
    fun getArticles(sources:String, apiKey:String): Single<ArticlesModel>
}