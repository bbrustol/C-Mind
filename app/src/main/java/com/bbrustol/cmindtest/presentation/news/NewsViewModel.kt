package com.bbrustol.cmindtest.presentation.news

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.bbrustol.cmindtest.data.model.NewsModel
import com.bbrustol.cmindtest.data.model.NewsUseCases
import com.bbrustol.cmindtest.data.model.SourcesModel
import com.bbrustol.cmindtest.data.model.emptyNewsModel
import com.bbrustol.cmindtest.di.SCHEDULER_IO
import com.bbrustol.cmindtest.di.SCHEDULER_MAIN_THREAD
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import javax.inject.Inject
import javax.inject.Named

private val TAG = NewsViewModel::class.java.name

class NewsViewModel
@Inject constructor(private val newsUseCases: NewsUseCases, @Named(SCHEDULER_IO) val subscribeOnScheduler:Scheduler, @Named(
    SCHEDULER_MAIN_THREAD
) val observeOnScheduler: Scheduler) : ViewModel() {

    private val log = AnkoLogger(this.javaClass)
    private var sources: ArrayList<SourcesModel> = arrayListOf()

    val stateLiveData =  MutableLiveData<NewsState>()
    var disposable: Disposable? = null
    private var mBase = ""

    init {
        stateLiveData.value = DefaultState(obtainCurrentData())
    }

    fun updateBase(base: String) {
        mBase = base
    }

    //region private methods
    @SuppressLint("CheckResult")
    fun getNews(apiKey: String) {
        newsUseCases.getNews(mBase)
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
            .subscribe(this::onSuccess, this::onError)

    }

    private fun onSuccess(news: NewsModel) {
        Log.d("aaaa", news.toString())
        stateLiveData.value = DefaultState(news)
    }

    private fun onError(error: Throwable) {
        log.warn { error.message }
        stateLiveData.value = ErrorState(error.message ?: "",  obtainCurrentData())
    }



    private fun obtainCurrentData() = stateLiveData.value?.news ?: emptyNewsModel
    //endregion
}