package com.bbrustol.cmindtest.data.model

import android.os.Parcelable
import io.reactivex.Single
import kotlinx.android.parcel.Parcelize

interface ArticlesUseCases {
    fun getArticles(sources: String, apiKey: String) : Single<ArticlesModel>
}

val emptyArticlesModel= ArticlesModel("", "", arrayListOf())

@Parcelize
data class ArticlesModel(
    val status: String,
    val totalResults: String,
    val articles: ArrayList<EverythingModel>) : Parcelable

@Parcelize
data class EverythingModel(
    val source: SourceArticleModel,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String) : Parcelable

@Parcelize
data class SourceArticleModel (
    val id: String,
    val name: String):Parcelable
