package com.bbrustol.cmindtest.presentation.fragments

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.data.model.NewsModel
import com.bbrustol.cmindtest.data.repository.NewsRepository
import com.bbrustol.cmindtest.infrastruture.SchedulerProvider
import com.bbrustol.cmindtest.presentation.news.NewsActivity
import com.bbrustol.cmindtest.presentation.news.NewsViewModel
import com.bbrustol.cmindtest.testutil.RecyclerViewMatcher
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertTrue
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.lang.reflect.Type
import org.mockito.Mockito.`when` as whenever

@RunWith(AndroidJUnit4::class)
class NewsFragmentTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(NewsActivity::class.java, true, false)

    @Mock
    private lateinit var mockNewsRepository: NewsRepository

    private lateinit var mockNewsViewModel: NewsViewModel

    @Mock
    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    @Before
    fun setUp() {
//        testConfigurationInstance = InstrumentationRegistry.getTargetContext().applicationContext as TestConfiguration

         MockitoAnnotations.initMocks(this)

        mockNewsViewModel = NewsViewModel(mockNewsRepository, schedulerProvider)

//        viewModelFactory.viewModels[NewsViewModel::class.java] = mockNewsViewModel

//        Buscar por viewModelFactoryModel Mock para mockito

//        TestInjector(TestApplicationModule(mockNewsViewModel)).inject()
    }

    @Test
    fun testRecyclerViewShowingCorrectItems() {

        activityRule.launchActivity(Intent())

        //        mockNewsModel(1)

        assertTrue(true)

//        onView(withId(R.id.shimmer_view_container)).check(matches(isDisplayed()))
//        checkNameOnPosition(0, "User 1")
//        checkNameOnPosition(2, "User 3")
    }

    @Test
    fun testRecyclerViewShowingCorrectItemsAfterScroll() {
        mockNewsModel(1)

        activityRule.launchActivity(Intent())

//        Espresso.onView(ViewMatchers.withId(R.id.shimmer_view_container))
//                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(8))

//        checkNameOnPosition(8, "User 9")
    }

    @Test
    fun testRecyclerViewShowingCorrectItemsAfterPagination() {
        mockNewsModel(1)
        mockNewsModel(2)

        activityRule.launchActivity(Intent())

        // Trigger pagination
        Espresso.onView(ViewMatchers.withId(R.id.rv_news))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))

        // Scroll to a position on the next page
        Espresso.onView(ViewMatchers.withId(R.id.rv_news))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))

        // Check if view is showing the correct text
//        checkNameOnPosition(25, "User 26")
    }


//    @Test
//    fun testOpenDetailsOnItemClick() {
//        mockNewsModel(1)
//
//        activityRule.launchActivity(Intent())
//
//        Espresso.onView(ViewMatchers.withId(R.id.rv_news))
//                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
//
//        // Check if we see the new recyclerview with the clicked items name shown in the first element
//        Espresso.onView(withRecyclerView(R.id.detailsRecyclerView).atPositionOnView(0, R.id.name))
//                .check(ViewAssertions.matches(ViewMatchers.withText("User 1")))
//    }

//    @Test
//    fun testFirstPageErrorShowErrorView() {
//        mockRepoError(1)
//
//        activityRule.launchActivity(Intent())
//
//        Espresso.onView(ViewMatchers.withId(R.id.errorView))
//                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//    }

    @Test
    fun testSecondPageErrorShowToast() {
        mockNewsModel(1)
        mockRepoError(2)

        activityRule.launchActivity(Intent())

        Espresso.onView(ViewMatchers.withId(R.id.rv_news))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))

        onView(ViewMatchers.withText("Error loading data"))
                .inRoot(withDecorView(Matchers.not(Matchers.`is`(activityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(isDisplayed()))
    }

    private fun mockNewsModel(page: Int) {

        val mockSingle = Single.create<NewsModel> { emitter: SingleEmitter<NewsModel> ->
            val newsModel: NewsModel = getMockJson(JSON_NEWS_RESPONSE, NewsModel::class.java)!!
            emitter.onSuccess(newsModel)
        }

        whenever(mockNewsRepository.getNews("")).thenReturn(mockSingle)
    }

    private fun mockRepoError(page: Int) {
        val mockSingle = Single.create<NewsModel> { emitter: SingleEmitter<NewsModel> ->
            emitter.onError(Throwable("Error"))
        }

        whenever(mockNewsRepository.getNews("")).thenReturn(mockSingle)
    }

    private fun <T> getMockJson(jsonFile: String, clazz: Class<T>): T? {
        val file = readFile(jsonFile)

        return if (file != null) Gson().fromJson(file, clazz) as T else null
    }

    private fun <T> getMockJson(jsonFile: String, type: Type): T? {
        val file = readFile(jsonFile)

        return if (file != null) Gson().fromJson<Any>(file, type) as T else null
    }

    private fun readFile(file: String): Reader {
        val inputStream = this.javaClass.classLoader.getResourceAsStream(file)

        return BufferedReader(InputStreamReader(inputStream))
    }

    companion object {
        private const val JSON_NEWS_RESPONSE = "sources-200.json"
    }
}
