package com.bbrustol.cmindtest

import com.bbrustol.cmindtest.di.DaggerAppComponent
import dagger.android.AndroidInjector

class AppConfiguration : dagger.android.DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out dagger.android.DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }
}
