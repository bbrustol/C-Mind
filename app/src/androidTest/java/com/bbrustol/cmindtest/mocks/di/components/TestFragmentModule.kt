package com.bbrustol.cmindtest.mocks.di.components

import com.bbrustol.cmindtest.presentation.news.NewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeNewsListFragment(): NewsFragment

//    @ContributesAndroidInjector
//    abstract fun contributeArticlesDetailsFragment(): ArticlesFragment
}