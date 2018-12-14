package com.bbrustol.cmindtest.presentation.news

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bbrustol.cmindtest.data.model.NewsModel
import com.bbrustol.cmindtest.data.model.NewsUseCases
import com.bbrustol.cmindtest.data.model.emptyNewsModel
import com.bbrustol.cmindtest.di.SCHEDULER_IO
import com.bbrustol.cmindtest.di.SCHEDULER_MAIN_THREAD
import io.reactivex.Scheduler
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.warn
import javax.inject.Inject
import javax.inject.Named

private val TAG = NewsViewModel::class.java.name

class NewsViewModel
@Inject constructor(private val newsUseCases: NewsUseCases, @Named(SCHEDULER_IO) val subscribeOnScheduler:Scheduler, @Named(
    SCHEDULER_MAIN_THREAD
) val observeOnScheduler: Scheduler) : ViewModel() {

    private val log = AnkoLogger(this.javaClass)

    val stateLiveData =  MutableLiveData<NewsState>()

    init {
        stateLiveData.value = InitState(obtainCurrentData(), true)
    }

    @SuppressLint("CheckResult")
    fun getNews(apiKey: String) {
        newsUseCases.getNews(apiKey)
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
            .subscribe(this::onSuccess, this::onError)
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