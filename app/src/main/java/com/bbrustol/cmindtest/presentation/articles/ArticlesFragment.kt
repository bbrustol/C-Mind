package com.bbrustol.cmindtest.presentation.articles

//import android.arch.lifecycle.Observer
//import android.arch.lifecycle.ViewModelProvider
//import android.arch.lifecycle.ViewModelProviders
//import android.content.Context
//import android.os.Bundle
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.bbrustol.cmindtest.BuildConfig
//import com.bbrustol.cmindtest.R
//import com.bbrustol.cmindtest.infrastruture.Constants
import com.bbrustol.cmindtest.presentation.BaseFragment
//import dagger.android.support.AndroidSupportInjection
//import io.reactivex.disposables.Disposable
//import kotlinx.android.synthetic.main.fragment_articles.view.*
//import kotlinx.android.synthetic.main.include_toolbar.*
//import org.jetbrains.anko.design.longSnackbar
//import javax.inject.Inject


val ARTICLES_FRAGMENT_TAG = ArticlesFragment::class.java.name

fun articlesFragment() = ArticlesFragment()

class ArticlesFragment : BaseFragment() {

//    @Inject
//    lateinit var mViewModelFactory: ViewModelProvider.Factory
//
//    private lateinit var mViewModel: ArticlesViewModel
//
//    private val mArticlesAdapter by lazy { ArticlesAdapter() }
//
//    private var mArticlesWebviewDisposse: Disposable? = null
//
//    private var mView: View? = null
//    private var mPage = 1
//    private var mSavedBundle: Bundle? = null
//    private var mFlagLoaing = false
//
//
//    fun newInstance(id: String): ArticlesFragment {
//        val fragment = ArticlesFragment()
//
//        val args = Bundle()
//        args.putString(Constants.ARGUMENT_ARTICLES_ID, id)
//        fragment.arguments = args
//
//        return fragment
//    }
//
//    //region private methods
//    private val stateObserver = Observer<ArticlesState> { state ->
//        state?.let {
//            when (state) {
//                is InitState -> {
//                    showShimmer(it.isShimmer)
//                    mFlagLoaing = true
//                    mView?.loading_articles?.visibility = View.VISIBLE
//                }
//                is DefaultState -> {
//                    showShimmer(it.isShimmer)
//                    configToolbar(it.articles.articles.first().source.name)
//                    mArticlesAdapter.updateData(it.articles.articles)
//                    mFlagLoaing = false
//                    mView?.loading_articles?.visibility = View.GONE
//                }
//                is ErrorState -> {
//                    showShimmer(it.isShimmer)
//                    mFlagLoaing = false
//                    mView?.loading_articles?.visibility = View.GONE
//                }
//            }
//        }
//    }
//
//    private fun configToolbar(title: String) {
//        toolbar.visibility = View.VISIBLE
//        toolbar.title = title
//        toolbar.setNavigationIcon(com.bbrustol.cmindtest.R.drawable.ic_arrow_back_white)
//        toolbar.setNavigationOnClickListener {
//            mPage = 1
//            activity?.finish()
//        }
//    }
//
//    private fun initializeRecyclerView() {
//        val linearLayoutManager = LinearLayoutManager(context)
//        mView?.rv_articles?.apply {
//            layoutManager = linearLayoutManager
//            addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                    if (isNearToLastItem(linearLayoutManager) && !mFlagLoaing) {
//                        if (checkConnection()) {
//                            mFlagLoaing = true
//                            mPage++
//                            callApiArticles(mPage)
//                            mView?.loading_articles?.visibility = View.VISIBLE
//                        }else {
//                            showShimmer(false)
//                            longSnackbar(mView!!, R.string.check_your_connection_and_try_again)
//                        }
//                    }
//                }
//            })
//            adapter = mArticlesAdapter
//        }
//    }
//
//    private fun isNearToLastItem(layoutManager: LinearLayoutManager): Boolean {
//        val visibleItemCount = layoutManager.childCount
//        val totalItemCount = layoutManager.itemCount
//
//        val firstVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
//
//        return visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0
//    }
//
//
//    private fun dismissObeservers() {
//        mArticlesWebviewDisposse?.dispose()
//        mViewModel.stateLiveData.removeObserver(stateObserver)
//    }
//
//    private fun setupItemClick() {
//        mArticlesWebviewDisposse = mArticlesAdapter.clickWebviewButtonEvent
//            .subscribe {
//                openWebview(it.url, it.url)
//            }
//    }
//
//    fun callApiArticles(page:Int) {
//        if (checkConnection()) {
//            mViewModel.getArticles(
//                arguments?.getString(Constants.ARGUMENT_ARTICLES_ID, "") ?: "",
//                page,
//                BuildConfig.API_KEY
//            )
//        }else {
//            showShimmer(false)
//            internetError(Constants.Error.ARGUMENT_CONNECTION_ERROR)
//        }
//    }
//
//    private fun saveBundle(): Bundle {
//        val state = Bundle()
//        state.putInt(Constants.ARGUMENT_ARTICLES_PAGE, mPage)
//        return state
//    }
//    //endregion
//
//    //region override methods
//    override fun onAttach(context: Context?) {
//        AndroidSupportInjection.inject(this)
//        super.onAttach(context)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ArticlesViewModel::class.java)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mViewModel.stateLiveData.observe(this, stateObserver)
//        setupItemClick()
//        callApiArticles(mPage)
//    }
//    override fun onDestroy() {
//        super.onDestroy()
//        mView?.rv_articles?.adapter = null
//        dismissObeservers()
//        mSavedBundle = saveBundle()
//        mFlagLoaing = false
//    }
//
//    override fun onPause() {
//        super.onPause()
//        dismissObeservers()
//    }
//
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putBundle(Constants.ARGUMENT_ARTICLES_PAGE, if (mSavedBundle != null) mSavedBundle else saveBundle())
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        mView = inflater.inflate(com.bbrustol.cmindtest.R.layout.fragment_articles, container, false)
//
//        if(savedInstanceState != null && mSavedBundle == null) {
//            mSavedBundle = savedInstanceState.getBundle(Constants.ARGUMENT_ARTICLES_PAGE)
//        }
//        if(mSavedBundle != null) {
//            mPage = mSavedBundle!!.getInt(Constants.ARGUMENT_ARTICLES_PAGE)
//        }
//        mSavedBundle = null
//
//        initializeRecyclerView()
//
//        return mView
//    }
    //endregion
}