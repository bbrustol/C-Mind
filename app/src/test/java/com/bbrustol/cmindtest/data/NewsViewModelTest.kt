package com.bbrustol.cmindtest.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.bbrustol.cmindtest.BaseTest
import com.bbrustol.cmindtest.data.model.NewsModel
import com.bbrustol.cmindtest.data.repository.NewsRepository
import com.bbrustol.cmindtest.infrastruture.SchedulerProvider
import com.bbrustol.cmindtest.presentation.news.NewsViewModel
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


/**
 * Unit test for [NewsViewModelTest].
 */
class NewsViewModelTest: BaseTest() {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockRepository: NewsRepository

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    private lateinit var newsViewModel: NewsViewModel

    private val newsModel: NewsModel
        get() = getMockJson(JSON_NEWS_RESPONSE, NewsModel::class.java)!!

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        newsViewModel = NewsViewModel(mockRepository, schedulerProvider)
    }

    @Test
    fun `News Model Test`() {
        Mockito.`when`(mockRepository.getNews("")).thenReturn(Single.just(newsModel))

        val testObserver = TestObserver<NewsModel>()

        newsViewModel.getSources("")
            .subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.assertValue { newsModel -> newsModel.status == "ok" }
        testObserver.assertValue { newsModel -> newsModel.sources[0].id == "abc-news" }
    }

    companion object {
        private const val JSON_NEWS_RESPONSE = "sources-200.json"
    }
}