package com.bbrustol.cmindtest.data.repository

import com.bbrustol.cmindtest.data.ApiService
import com.bbrustol.cmindtest.data.model.ArticlesModel
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlesRepository @Inject constructor(private val apiService: ApiService) {
    fun getArticles(sources:String, page: Int, apiKey:String): Single<ArticlesModel> = apiService.getEverything(sources, page, apiKey)
}