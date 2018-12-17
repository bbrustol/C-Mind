package com.bbrustol.cmindtest.presentation.news

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bbrustol.cmindtest.BuildConfig
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.presentation.articles.articlesActivity
import com.bbrustol.cmindtest.presentation.webview.webviewActivity
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_news.view.*
import kotlinx.android.synthetic.main.include_shimmer.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.error
import org.jetbrains.anko.warn
import javax.inject.Inject

val NEWS_FRAGMENT_TAG = NewsFragment::class.java.name

class NewsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: NewsViewModel

    private val log = AnkoLogger(this.javaClass)

    private val newsAdapter by lazy { NewsAdapter() }

    private var mView: View? = null

    private var newsWebviewDisposse: Disposable? = null
    private var newsItemRecyclerDisposse: Disposable? = null

    //region private methods
    private val stateObserver = Observer<NewsState> { state ->
        state?.let {
            when (state) {
                is InitState -> {
                    showShimmer(viewModel.stateLiveData.value!!.isShimmer)
                    log.debug { "init -> " + viewModel.stateLiveData.value!!.isShimmer }
                }
                is DefaultState -> {
                    showShimmer(viewModel.stateLiveData.value!!.isShimmer)
                    log.warn { "default -> " +  viewModel.stateLiveData.value!!.isShimmer }
                    newsAdapter.updateData(it.news.sources)
                }
                is ErrorState -> {
                    showShimmer(viewModel.stateLiveData.value!!.isShimmer)
                    log.error { "error -> " +  viewModel.stateLiveData.value }
                }
            }
        }
    }

    private fun initializeRecyclerView() {
        mView?.rv_news?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    private fun dismissObeservers() {
        newsWebviewDisposse?.dispose()
        newsItemRecyclerDisposse?.dispose()
        viewModel.stateLiveData.removeObserver(stateObserver)
    }

    private fun setupItemClick() {
        newsWebviewDisposse = newsAdapter.clickWebviewButtonEvent
            .subscribe {
                log.debug { "on click link recycled (webview) -> %it"}
                startActivity(webviewActivity().getLaunchingIntent(context,it))
            }

        newsItemRecyclerDisposse = newsAdapter.clickItemEvent
            .subscribe {
                log.debug { "on click item recycled -> %it"}
                startActivity(articlesActivity().getLaunchingIntent(context, it))
            }
    }

    private fun showShimmer (flag: Boolean) {
        shimmer_view_container.apply {
            if (flag) {
                startShimmerAnimation()
                visibility = View.VISIBLE
            } else {
                stopShimmerAnimation()
                visibility = View.GONE
            }
        }
    }

    //endregion

    //region override methods
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsViewModel::class.java)
        viewModel.getNews(BuildConfig.API_KEY)
    }

    override fun onResume() {
        super.onResume()
        viewModel.stateLiveData.observe(this, stateObserver)
        setupItemClick()
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
        initializeRecyclerView()
        return mView
    }
    //endregion
}