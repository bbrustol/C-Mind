package com.bbrustol.cmindtest.presentation.articles

import com.bbrustol.cmindtest.data.model.ArticlesModel

sealed class ArticlesState {
    abstract var articles: ArticlesModel
    abstract var isShimmer: Boolean
}

data class InitState(override var articles: ArticlesModel, override var isShimmer: Boolean) : ArticlesState()

data class DefaultState(override var articles: ArticlesModel, override var isShimmer: Boolean) : ArticlesState()

data class ErrorState(val errorMessage: String, override var articles: ArticlesModel, override var isShimmer: Boolean) : ArticlesState()