package com.bbrustol.cmindtest.presentation.articles

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bbrustol.cmindtest.data.model.ArticlesModel
import com.bbrustol.cmindtest.data.model.ArticlesUseCases
import com.bbrustol.cmindtest.data.model.emptyArticlesModel
import com.bbrustol.cmindtest.di.SCHEDULER_IO
import com.bbrustol.cmindtest.di.SCHEDULER_MAIN_THREAD
import io.reactivex.Scheduler
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.error
import javax.inject.Inject
import javax.inject.Named

private val TAG = ArticlesViewModel::class.java.name

class ArticlesViewModel
@Inject constructor(private val articlesUseCases: ArticlesUseCases, @Named(SCHEDULER_IO) val subscribeOnScheduler:Scheduler, @Named(
    SCHEDULER_MAIN_THREAD
) val observeOnScheduler: Scheduler) : ViewModel() {

    private val log = AnkoLogger(this.javaClass)

    val stateLiveData =  MutableLiveData<ArticlesState>()

    init {
        stateLiveData.value = InitState(obtainCurrentData(), true)
    }

    @SuppressLint("CheckResult")
    fun getArticles(sources: String, page: Int, apiKey: String) {
        articlesUseCases.getArticles(sources, page, apiKey)
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
            .subscribe(this::onSuccess, this::onError)
    }

    //region private methods
    private fun onSuccess(articles: ArticlesModel) {
        log.debug { articles.toString() }
        increseArticles(articles)
    }

    private fun increseArticles(articles: ArticlesModel) {
        if (articles.status == "ok") {
            val articlesModelUpdate = stateLiveData.value?.articles
            for (model in articles.articles) {
                articlesModelUpdate?.articles?.add(model)
            }

            stateLiveData.value = DefaultState(articlesModelUpdate!!, false)
        }
    }

    private fun onError(error: Throwable) {
        log.error { error.message }
        stateLiveData.value = ErrorState(error.message ?: "",  obtainCurrentData(), false)
    }

    private fun obtainCurrentData() = stateLiveData.value?.articles ?: emptyArticlesModel
    //endregion
}