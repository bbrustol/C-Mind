package com.bbrustol.cmindtest.view.fragments

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Intent
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.bbrustol.cmindtest.data.model.ArticlesModel
import com.bbrustol.cmindtest.data.repository.NewsRepository
import com.bbrustol.cmindtest.infrastruture.SchedulerProvider
import com.bbrustol.cmindtest.mocks.di.modules.TestApplicationModule
import com.bbrustol.cmindtest.presentation.news.NewsActivity
import com.bbrustol.cmindtest.presentation.news.NewsViewModel
import com.bbrustol.cmindtest.testutil.RecyclerViewMatcher
import com.bbrustol.cmindtest.testutil.TestInjector
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertTrue
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
class UserListFragmentTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(NewsActivity::class.java, true, false)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockRepository: NewsRepository

    private lateinit var newsViewModel: NewsViewModel

    private val articlesModel200: ArticlesModel
        get() = getMockJson(JSON_NEWS_RESPONSE, ArticlesModel::class.java)!!

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())


//    @Mock
//    private lateinit var mockArticlesRepository: ArticlesRepository

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        newsViewModel = NewsViewModel(mockRepository, schedulerProvider)
        TestInjector(TestApplicationModule()).inject()
    }

    @Test
    fun testRecyclerViewShowingCorrectItems() {
        assertTrue(true)
//        mockRepoUsers(1)

        activityRule.launchActivity(Intent())

//        checkNameOnPosition(0, "User 1")
//        checkNameOnPosition(2, "User 3")
    }

//    @Test
//    fun testRecyclerViewShowingCorrectItemsAfterScroll() {
//        mockRepoUsers(1)
//
//        activityRule.launchActivity(Intent())
//
//        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
//                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(8))
//
//        checkNameOnPosition(8, "User 9")
//    }

//    @Test
//    fun testRecyclerViewShowingCorrectItemsAfterPagination() {
//        mockRepoUsers(1)
//        mockRepoUsers(2)
//
//        activityRule.launchActivity(Intent())
//
//        // Trigger pagination
//        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
//                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))
//
//        // Scroll to a position on the next page
//        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
//                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(25))
//
//        // Check if view is showing the correct text
//        checkNameOnPosition(25, "User 26")
//    }
//
//    private fun checkNameOnPosition(position: Int, expectedName: String) {
//        Espresso.onView(withRecyclerView(R.id.recyclerView).atPositionOnView(position, R.id.name))
//                .check(ViewAssertions.matches(ViewMatchers.withText(expectedName)))
//    }
//
//    @Test
//    fun testOpenDetailsOnItemClick() {
//        mockRepoUsers(1)
//
//        activityRule.launchActivity(Intent())
//
//        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
//                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
//
//        // Check if we see the new recyclerview with the clicked items name shown in the first element
//        Espresso.onView(withRecyclerView(R.id.detailsRecyclerView).atPositionOnView(0, R.id.name))
//                .check(ViewAssertions.matches(ViewMatchers.withText("User 1")))
//    }
//
//    @Test
//    fun testFirstPageErrorShowErrorView() {
//        mockRepoError(1)
//
//        activityRule.launchActivity(Intent())
//
//        Espresso.onView(ViewMatchers.withId(R.id.errorView))
//                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//    }
//
//    @Test
//    fun testSecondPageErrorShowToast() {
//        mockRepoUsers(1)
//        mockRepoError(2)
//
//        activityRule.launchActivity(Intent())
//
//        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
//                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))
//
//        onView(ViewMatchers.withText("Error loading data"))
//                .inRoot(withDecorView(Matchers.not(Matchers.`is`(activityRule.getActivity().getWindow().getDecorView()))))
//                .check(ViewAssertions.matches(isDisplayed()))
//    }

//    private fun mockRepoUsers(page: Int) {
//        val users = (1..20L).map {
//            val number = (page - 1) * 20 + it
//            User(it, "User $number", number * 100, "")
//        }
//
//        val mockSingle = Single.create<UserListModel> { emitter: SingleEmitter<UserListModel> ->
//            val userListModel = UserListModel(users)
//            emitter.onSuccess(userListModel)
//        }
//
//        whenever(mockNewsRepository.getUsers(page, false)).thenReturn(mockSingle)
//    }

//    private fun mockRepoError(page: Int) {
//        val mockSingle = Single.create<UserListModel> { emitter: SingleEmitter<UserListModel> ->
//            emitter.onError(Throwable("Error"))
//        }
//
//        whenever(mockNewsRepository.getUsers(page, false)).thenReturn(mockSingle)
//    }

    protected fun <T> getMockJson(jsonFile: String, clazz: Class<T>): T? {
        val file = readFile(jsonFile)

        return if (file != null) Gson().fromJson(file, clazz) as T else null
    }

    protected fun <T> getMockJson(jsonFile: String, type: Type): T? {
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