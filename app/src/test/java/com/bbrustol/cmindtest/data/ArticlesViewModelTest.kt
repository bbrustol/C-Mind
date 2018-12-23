package com.bbrustol.cmindtest.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.bbrustol.cmindtest.BaseTest
import com.bbrustol.cmindtest.data.model.ArticlesModel
import com.bbrustol.cmindtest.data.repository.ArticlesRepository
import com.bbrustol.cmindtest.infrastruture.SchedulerProvider
import com.bbrustol.cmindtest.presentation.articles.ArticlesViewModel
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
 * Unit test for [ArticlesViewModelTest].
 */
class ArticlesViewModelTest: BaseTest() {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockRepository: ArticlesRepository

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    private lateinit var articlesViewModel: ArticlesViewModel

    private val articlesModel: ArticlesModel
        get() = getMockJson(JSON_ARTICLES_RESPONSE, ArticlesModel::class.java)!!

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        articlesViewModel = ArticlesViewModel(mockRepository, schedulerProvider)
    }

    @Test
    fun `Articles Model Test`() {

        Mockito.`when`(mockRepository.getArticles("axios",1,"")).thenReturn(Single.just(articlesModel))

        val testObserver = TestObserver<ArticlesModel>()

        articlesViewModel.getEverything("axios",1,"")
                .subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.assertValue { articlesModel -> articlesModel.status == "ok" }
        testObserver.assertValue { newsModel -> articlesModel.totalResults == "115" }
        testObserver.assertValue { newsModel -> articlesModel.articles[0].source.id == "axios" }
        testObserver.assertValue { newsModel -> articlesModel.articles[0].urlToImage == "https://images.axios.com/5oOhsKt34gPx_x-dEQGokRt6K3U=/0x0:1920x1080/1920x1080/2018/12/21/1545428600630.jpg" }
    }

    @Test
    fun `Articles Model Error Test`() {

        Mockito.`when`(mockRepository.getArticles("axios",1,"")).thenReturn(Single.just(articlesModel))

        val testObserver = TestObserver<ArticlesModel>()

        articlesViewModel.getEverything("axios",1,"")
            .subscribe(testObserver)

        testObserver.assertNoErrors()

        testObserver.assertValue { articlesModel -> articlesModel.status == "ok" }
        testObserver.assertValue { newsModel -> articlesModel.totalResults == "115" }
        testObserver.assertValue { newsModel -> articlesModel.articles[0].source.id == "axios" }
        testObserver.assertValue { newsModel -> articlesModel.articles[0].urlToImage == "https://images.axios.com/5oOhsKt34gPx_x-dEQGokRt6K3U=/0x0:1920x1080/1920x1080/2018/12/21/1545428600630.jpg" }
    }

    companion object {
        private const val JSON_ARTICLES_RESPONSE = "articles-200.json"
    }
}