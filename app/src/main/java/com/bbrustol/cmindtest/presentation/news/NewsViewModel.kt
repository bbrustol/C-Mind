package com.bbrustol.cmindtest.presentation.news

import android.arch.lifecycle.MutableLiveData
import com.bbrustol.cmindtest.data.model.NewsModel
import com.bbrustol.cmindtest.data.model.emptyNewsModel
import com.bbrustol.cmindtest.data.repository.NewsRepository
import com.bbrustol.cmindtest.infrastruture.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.warn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsViewModel @Inject constructor(private val repository: NewsRepository, private val schedulerProvider: SchedulerProvider) {

    private val log = AnkoLogger(this.javaClass)

    val stateLiveData =  MutableLiveData<NewsState>()

    val compositeDisposable by lazy { CompositeDisposable() }

    init {
        stateLiveData.value = InitState(obtainCurrentData(), true)
    }

    fun getSources(apiKey: String) = repository.getNews(apiKey)
        .compose(schedulerProvider.getSchedulersForSingle())

    fun getNews(apiKey: String) {
        compositeDisposable.add(getSources(apiKey)
            .subscribe(this::onSuccess, this::onError))
    }

    //region private methods
    private fun onSuccess(news: NewsModel) {
        log.debug { news.toString() }
        stateLiveData.value = DefaultState(news, false)
    }

    private fun onError(error: Throwable) {
        log.warn { error.message }
        stateLiveData.value = ErrorState(error.message ?: "",  obtainCurrentData(), false)
    }

    private fun obtainCurrentData() = stateLiveData.value?.news ?: emptyNewsModel
    //endregion
}