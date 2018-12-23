package com.bbrustol.cmindtest.presentation.articles

import android.arch.lifecycle.MutableLiveData
import com.bbrustol.cmindtest.data.model.ArticlesModel
import com.bbrustol.cmindtest.data.model.emptyArticlesModel
import com.bbrustol.cmindtest.data.repository.ArticlesRepository
import com.bbrustol.cmindtest.infrastruture.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.error

class ArticlesViewModel (private val repository: ArticlesRepository, private val schedulerProvider: SchedulerProvider) {
    private val log = AnkoLogger(this.javaClass)
    var articlesModelUpdate: ArticlesModel = emptyArticlesModel

    val compositeDisposable by lazy { CompositeDisposable() }
    var stateLiveData = MutableLiveData<ArticlesState>()

    init {
        stateLiveData.value = InitState(obtainCurrentData(), true)
    }

    fun getEverything(sources: String, page: Int, apiKey: String) = repository.getArticles(sources, page, apiKey)
        .compose(schedulerProvider.getSchedulersForSingle())

    fun getArticles(sources: String, page: Int, apiKey: String) =
        compositeDisposable.add(getEverything(sources, page, apiKey)
            .subscribe(this::onSuccess, this::onError))

    fun getIncresedArticles() = articlesModelUpdate.articles

    //region private methods
    private fun increseArticles(articles: ArticlesModel) {
        for (model in articles.articles) {
            articlesModelUpdate.articles.add(model)
        }
    }

    private fun onSuccess(articles: ArticlesModel) {
        log.debug { articles.toString() }
        increseArticles(articles)
        stateLiveData.value = DefaultState(articles, false)
    }

    private fun onError(error: Throwable) {
        log.error { error.message }
        stateLiveData.value = ErrorState(error.message ?: "",  obtainCurrentData(), false)
    }

    private fun obtainCurrentData() = stateLiveData.value?.articles ?: emptyArticlesModel
    //endregion
}