package com.bbrustol.cmindtest.presentation.webview.di

import android.app.Activity
import com.bbrustol.cmindtest.presentation.webview.WebviewActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap


@Module(subcomponents = [WebviewActivitySubcomponent::class])
abstract class WebviewActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(WebviewActivity::class)
    abstract fun bindNewsActivityInjectorFactory(builder: WebviewActivitySubcomponent.Builder): AndroidInjector.Factory<out Activity>
}