package com.bbrustol.cmindtest.presentation.articles

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
import com.bbrustol.cmindtest.infrastruture.Constants
import com.bbrustol.cmindtest.presentation.webview.webviewActivity
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.android.synthetic.main.fragment_articles.view.*
import kotlinx.android.synthetic.main.include_shimmer.*
import kotlinx.android.synthetic.main.include_toolbar.*
import javax.inject.Inject

val ARTICLES_FRAGMENT_TAG = ArticlesFragment::class.java.name

class ArticlesFragment : Fragment() {

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    private lateinit var mViewModel: ArticlesViewModel

    private val mArticlesAdapter by lazy { ArticlesAdapter() }

    private var mArticlesWebviewDisposse: Disposable? = null

    private var mView: View? = null
    private var mPage = 1
    private var mSavedBundle: Bundle? = null


    fun newInstance(id: String): ArticlesFragment {
        val fragment = ArticlesFragment()

        val args = Bundle()
        args.putString(Constants.ARGUMENT_ARTICLES_ID, id)
        fragment.arguments = args

        return fragment
    }

    //region private methods
    private val stateObserver = Observer<ArticlesState> { state ->
        state?.let {
            when (state) {
                is InitState -> {
                    showShimmer(it.isShimmer)
                }
                is DefaultState -> {
                    showShimmer(it.isShimmer)
                    configToolbar(it.articles.articles.first().source.name)
                    mArticlesAdapter.updateData(it.articles.articles)
                    swipe_refresh_layout_articles.isRefreshing = false
                }
                is ErrorState -> {
                    showShimmer(it.isShimmer)
                    swipe_refresh_layout_articles.isRefreshing = false
                }
            }
        }
    }

    private fun configToolbar(title: String) {
        toolbar.visibility = View.VISIBLE
        toolbar.title = title
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener {
            mPage = 1
            activity?.finish()
        }
    }

    private fun initializeRecyclerView() {
        mView?.swipe_refresh_layout_articles?.setOnRefreshListener {
            mPage++
            callApiArticles(mPage)
        }

        mView?.rv_articles?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mArticlesAdapter
        }
    }

    private fun dismissObeservers() {
        mArticlesWebviewDisposse?.dispose()
        mViewModel.stateLiveData.removeObserver(stateObserver)
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

    private fun setupItemClick() {
        mArticlesWebviewDisposse = mArticlesAdapter.clickWebviewButtonEvent
            .subscribe {
                startActivity(webviewActivity().getLaunchingIntent(context,it))
            }
    }

    private fun callApiArticles(page:Int) {
        mViewModel.getArticles(
            arguments?.getString(Constants.ARGUMENT_ARTICLES_ID, "") ?: "",
            page,
            BuildConfig.API_KEY
        )
    }

    private fun saveBundle(): Bundle {
        val state = Bundle()
        state.putInt(Constants.ARGUMENT_ARTICLES_PAGE, mPage)
        return state
    }
    //endregion

    //region override methods
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ArticlesViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        mViewModel.stateLiveData.observe(this, stateObserver)
        setupItemClick()
        callApiArticles(mPage)
    }
    override fun onDestroy() {
        super.onDestroy()
        mView?.rv_articles?.adapter = null
        dismissObeservers()
        mSavedBundle = saveBundle()
    }

    override fun onPause() {
        super.onPause()
        dismissObeservers()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(Constants.ARGUMENT_ARTICLES_PAGE, if (mSavedBundle != null) mSavedBundle else saveBundle())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(com.bbrustol.cmindtest.R.layout.fragment_articles, container, false)

        if(savedInstanceState != null && mSavedBundle == null) {
            mSavedBundle = savedInstanceState.getBundle(Constants.ARGUMENT_ARTICLES_PAGE)
        }
        if(mSavedBundle != null) {
            mPage = mSavedBundle!!.getInt(Constants.ARGUMENT_ARTICLES_PAGE)
        }
        mSavedBundle = null

        initializeRecyclerView()

        return mView
    }
    //endregion
}