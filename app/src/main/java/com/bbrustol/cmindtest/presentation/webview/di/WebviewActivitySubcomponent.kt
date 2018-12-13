package com.bbrustol.cmindtest.presentation.webview.di

import com.bbrustol.cmindtest.presentation.webview.WebviewActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector


@Subcomponent(modules = arrayOf(WebviewFragmentModule::class))
interface WebviewActivitySubcomponent : AndroidInjector<WebviewActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<WebviewActivity>()
}