package com.bbrustol.cmindtest.presentation.news

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment
import com.bbrustol.cmindtest.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import com.bbrustol.cmindtest.infrastruture.replaceFragment

class NewsActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private val newsFragment by lazy { newsFragment() }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        if (savedInstanceState == null) replaceFragment(R.id.framelayout_container, newsFragment, NEWS_FRAGMENT_TAG)
    }
}
