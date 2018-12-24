package com.bbrustol.cmindtest.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

val emptyNewsModel= NewsModel("", arrayListOf())

@Parcelize
data class NewsModel(
    val status: String,
    val sources: ArrayList<SourcesModel>) : Parcelable


@Parcelize
data class SourcesModel(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String) : Parcelable