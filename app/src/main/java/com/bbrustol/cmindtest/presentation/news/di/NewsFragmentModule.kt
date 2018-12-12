package com.bbrustol.cmindtest.presentation.news.di

import android.support.v4.app.Fragment
import com.bbrustol.cmindtest.presentation.news.NewsFragment
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Subcomponent/*(modules = ...)*/
interface NewsFragmentSubcomponent: AndroidInjector<NewsFragment> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<NewsFragment>()
}

@Module(subcomponents = arrayOf(NewsFragmentSubcomponent::class))
abstract class NewsFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(NewsFragment::class)
    abstract fun bindNewsFragmentInjectorFactory(builder: NewsFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>
}