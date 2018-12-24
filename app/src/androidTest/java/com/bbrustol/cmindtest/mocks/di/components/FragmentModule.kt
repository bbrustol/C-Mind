package com.bbrustol.cmindtest.mocks.di.components

import com.bbrustol.cmindtest.presentation.news.NewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeNewsFragment(): NewsFragment

//    @ContributesAndroidInjector
//    abstract fun contributeArticlesFragment(): ArticlesFragment
}