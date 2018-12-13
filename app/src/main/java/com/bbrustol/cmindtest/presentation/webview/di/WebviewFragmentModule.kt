package com.bbrustol.cmindtest.presentation.webview.di

import android.support.v4.app.Fragment
import com.bbrustol.cmindtest.presentation.webview.WebviewFragment
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Subcomponent/*(modules = ...)*/
interface WebviewFragmentSubcomponent: AndroidInjector<WebviewFragment> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<WebviewFragment>()
}

@Module(subcomponents = arrayOf(WebviewFragmentSubcomponent::class))
abstract class WebviewFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(WebviewFragment::class)
    abstract fun bindWebviewFragmentInjectorFactory(builder: WebviewFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>
}