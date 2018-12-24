package com.bbrustol.cmindtest.testutil

import android.support.v4.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class TestApplication : DaggerAppCompatActivity() {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = fragmentInjector
}