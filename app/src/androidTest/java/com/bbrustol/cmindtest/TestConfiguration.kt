package com.bbrustol.cmindtest

import com.bbrustol.cmindtest.di.DaggerAppTestComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class TestConfiguration : dagger.android.DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppTestComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }
}