package com.bbrustol.cmindtest.mocks.di

import android.app.Activity
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class DaggerTestInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<android.support.v4.app.Fragment>
    @Inject
    lateinit var supportFragmentInjector:
            DispatchingAndroidInjector<android.support.v4.app.Fragment>
}