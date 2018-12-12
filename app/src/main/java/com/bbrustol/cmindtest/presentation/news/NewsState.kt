package com.bbrustol.cmindtest.presentation.news

import com.bbrustol.cmindtest.data.model.NewsModel

sealed class NewsState {
    abstract var news: NewsModel
}

data class DefaultState(override var news: NewsModel) : NewsState()

data class ErrorState(val errorMessage: String, override var news: NewsModel) : NewsState()