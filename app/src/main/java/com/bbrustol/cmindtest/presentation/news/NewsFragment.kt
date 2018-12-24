package com.bbrustol.cmindtest.presentation.news

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bbrustol.cmindtest.BuildConfig
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.infrastruture.Constants
import com.bbrustol.cmindtest.presentation.BaseFragment
import com.bbrustol.cmindtest.presentation.articles.ARTICLES_FRAGMENT_TAG
import com.bbrustol.cmindtest.presentation.articles.ArticlesFragment
import com.bbrustol.cmindtest.presentation.articles.articlesActivity
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_general.*
import kotlinx.android.synthetic.main.fragment_news.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.error
import org.jetbrains.anko.warn
import javax.inject.Inject

val NEWS_FRAGMENT_TAG = NewsFragment::class.java.name

class NewsFragment : BaseFragment() {

    lateinit var mViewModel: NewsViewModel

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    private val mLog = AnkoLogger(this.javaClass)

    private val mNewsAdapter by lazy { NewsAdapter() }

    private var mFlagFirst = true

    private var mView: View? = null

    private var mNewsWebviewDisposse: Disposable? = null
    private var mNewsItemRecyclerDisposse: Disposable? = null

    //region private methods
    private val stateObserver = Observer<NewsState> { state ->
        state?.let {
            when (state) {
                is InitState -> {
                    showShimmer(mViewModel.stateLiveData.value!!.isShimmer)
                    mLog.debug { "init -> " + mViewModel.stateLiveData.value!!.isShimmer }
                }
                is DefaultState -> {
                    showShimmer(mViewModel.stateLiveData.value!!.isShimmer)
                    mLog.warn { "default -> " +  mViewModel.stateLiveData.value!!.isShimmer }
                    mNewsAdapter.updateData(it.news.sources)

                    if (mFlagFirst && activity?.linear_dual_pane != null) {
                        replaceFragment(it.news.sources[0].id)
                    }
                    mFlagFirst = false
                }
                is ErrorState -> {
                    showShimmer(mViewModel.stateLiveData.value!!.isShimmer)
                    mLog.error { "error -> " +  mViewModel.stateLiveData.value }
                }
            }
        }
    }

    private fun initializeRecyclerView() {
        mView?.rv_news?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mNewsAdapter
        }
    }

    private fun dismissObeservers() {

        mViewModel.compositeDisposable.clear()
        mViewModel.compositeDisposable.dispose()

        mNewsWebviewDisposse?.dispose()
        mNewsItemRecyclerDisposse?.dispose()
        mViewModel.stateLiveData.removeObserver(stateObserver)
    }

    private fun setupItemClick() {
        mNewsWebviewDisposse = mNewsAdapter.clickWebviewButtonEvent
            .subscribe {
                openWebview(it.url, it.url)
            }

        mNewsItemRecyclerDisposse = mNewsAdapter.clickItemEvent
            .subscribe {
                verifyScreen(it)
            }
    }

    private fun verifyScreen(it: String) {
        if (activity?.linear_dual_pane != null) {
            replaceFragment(it)
        }else {
            mLog.debug { "on click item recycled -> %it"}
            startActivity(articlesActivity().getLaunchingIntent(context, it))
        }
    }

    private fun replaceFragment(it: String) {
        activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(
                R.id.framelayout_articles,
                ArticlesFragment().newInstance(it, false),
                ARTICLES_FRAGMENT_TAG
            )
            ?.commit()
    }
    //endregion

    //region override methods
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        mViewModel.stateLiveData.observe(this, stateObserver)
        setupItemClick()

        if (checkConnection()) {
            mViewModel.getNews(BuildConfig.API_KEY)
        }else {
            showShimmer(false)
            internetError(Constants.Error.ARGUMENT_CONNECTION_ERROR)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mView?.rv_news?.adapter = null
        dismissObeservers()
    }

    override fun onPause() {
        super.onPause()
        dismissObeservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_news, container, false)

        mViewModel = ViewModelProviders.of(this, mViewModelFactory
        )[NewsViewModel::class.java]

        initializeRecyclerView()
        mFlagFirst = true
        return mView
    }
    //endregion
}