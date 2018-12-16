package com.bbrustol.cmindtest.presentation.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.infrastruture.replaceFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.include_toolbar.*
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
        if (savedInstanceState == null) replaceFragment(R.id.framelayout_container, newsFragment, NEWS_FRAGMENT_TAG)
    }
}
