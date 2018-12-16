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
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.android.synthetic.main.fragment_articles.view.*
import kotlinx.android.synthetic.main.include_shimmer.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.warn
import javax.inject.Inject

val ARTICLES_FRAGMENT_TAG = ArticlesFragment::class.java.name

class ArticlesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ArticlesViewModel

    private val log = AnkoLogger(this.javaClass)

    private val articlesAdapter by lazy { ArticlesAdapter() }

    private var mView: View? = null

    fun newInstance(id: String): ArticlesFragment {
        val fragment = ArticlesFragment()

        val args = Bundle()
        args.putString(Constants.ARGUMENT_ARTICLE_ID, id)
        fragment.arguments = args

        return fragment
    }

    //region private methods
    private val stateObserver = Observer<ArticlesState> { state ->
        state?.let {
            when (state) {
                is InitState -> {
                    showShimmer(it.isShimmer)
                    log.debug { "init -> " + viewModel.stateLiveData.value!!.isShimmer }
                }
                is DefaultState -> {
                    showShimmer(it.isShimmer)
                    log.warn { "default -> " +  it.articles.articles.first().source.name }
                    configToolbar(it.articles.articles.first().source.name)
                    articlesAdapter.updateData(it.articles.articles)
                }
                is ErrorState -> {
                    showShimmer(it.isShimmer)
                    log.warn { "error -> " +  it.isShimmer }
                }
            }
        }
    }

    private fun configToolbar(title: String) {
        toolbar.visibility = View.VISIBLE
        toolbar.title = title
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener {
            activity?.finish()
        }
    }

    private fun initializeRecyclerView() {
        mView?.rv_articles?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articlesAdapter
        }
    }

    private fun dismissObeservers() {
        viewModel.stateLiveData.removeObserver(stateObserver)
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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ArticlesViewModel::class.java)
        viewModel.getArticles(arguments?.getString(Constants.ARGUMENT_ARTICLE_ID, "vazio") ?: "", BuildConfig.API_KEY)
    }

    override fun onResume() {
        super.onResume()
        viewModel.stateLiveData.observe(this, stateObserver)
    }
    override fun onDestroy() {
        super.onDestroy()
        mView?.rv_articles?.adapter = null
        dismissObeservers()
    }

    override fun onPause() {
        super.onPause()
        dismissObeservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_articles, container, false)
        initializeRecyclerView()
        return mView
    }
    //endregion
}