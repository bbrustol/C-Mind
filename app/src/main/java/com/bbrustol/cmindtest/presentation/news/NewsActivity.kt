package com.bbrustol.cmindtest.presentation.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.infrastruture.replaceFragment
import com.bbrustol.cmindtest.presentation.articles.ARTICLES_FRAGMENT_TAG
import com.bbrustol.cmindtest.presentation.articles.ArticlesFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_general.*
import javax.inject.Inject

class NewsActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private val newsFragment by lazy { NewsFragment() }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)

        if (savedInstanceState == null)

        if (linear_dual_pane == null) {
            replaceFragment(R.id.framelayout_container, newsFragment, NEWS_FRAGMENT_TAG)
        }else {
            replaceFragment(R.id.framelayout_news, newsFragment, NEWS_FRAGMENT_TAG)
            replaceFragment(R.id.framelayout_articles, ArticlesFragment().newInstance(""), ARTICLES_FRAGMENT_TAG)
        }
    }
}
