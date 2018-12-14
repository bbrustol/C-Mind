package com.bbrustol.cmindtest.presentation.news

import com.bbrustol.cmindtest.data.model.NewsModel

sealed class NewsState {
    abstract var news: NewsModel
    abstract var isShimmer: Boolean
}

data class InitState(override var news: NewsModel, override var isShimmer: Boolean) : NewsState()

data class DefaultState(override var news: NewsModel, override var isShimmer: Boolean) : NewsState()

data class ErrorState(val errorMessage: String, override var news: NewsModel, override var isShimmer: Boolean) : NewsState()