package com.bbrustol.cmindtest.presentation.news

import android.os.Bundle
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.infrastruture.replaceFragment
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector

class NewsActivity : DaggerAppCompatActivity(), HasSupportFragmentInjector {
    private val newsFragment by lazy { NewsFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)


        if (savedInstanceState == null)
            replaceFragment(R.id.framelayout_container, newsFragment, NEWS_FRAGMENT_TAG)
    }
}