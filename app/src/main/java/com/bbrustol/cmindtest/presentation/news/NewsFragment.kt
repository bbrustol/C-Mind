package com.bbrustol.cmindtest.presentation.news

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bbrustol.cmindtest.BuildConfig
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.presentation.webview.webviewActivity

import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_news.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn

import javax.inject.Inject

val NEWS_FRAGMENT_TAG = NewsFragment::class.java.name

private val TAG = NewsFragment::class.java.name

fun newsFragment() = NewsFragment()

class NewsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val log = AnkoLogger(this.javaClass)

    private lateinit var viewModel: NewsViewModel

    private val newsAdapter by lazy { NewsAdapter() }

    private var newsWebviewDisposse: Disposable? = null
    private var newsItemRecyclerDisposse: Disposable? = null

    private val stateObserver = Observer<NewsState> { state ->
        state?.let {
            when (state) {
                is DefaultState -> {
                    newsAdapter.updateData(it.news.sources)
                }
                is ErrorState -> {
                    log.warn { viewModel.stateLiveData.value }
                }
            }
        }
    }

    private fun initializeRecyclerView(view:View) {
        view.rv_news.apply {
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
                Log.d(TAG,it)
                startActivity(webviewActivity().getLaunchingIntent(context,it))
            }

        newsItemRecyclerDisposse = newsAdapter.clickItemEvent
            .subscribe {
                Log.d(TAG,it)
            }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsViewModel::class.java)
        viewModel.getNews(BuildConfig.NEWS_API_KEY)
    }

    override fun onResume() {
        super.onResume()
        viewModel.stateLiveData.observe(this, stateObserver)
        setupItemClick()
    }
    override fun onDestroy() {
        super.onDestroy()
        dismissObeservers()
    }

    override fun onPause() {
        super.onPause()
        dismissObeservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        initializeRecyclerView(view)
        return view
    }
}