package com.bbrustol.cmindtest.presentation.news.di

import com.bbrustol.cmindtest.presentation.articles.di.ArticlesFragmentModule
import com.bbrustol.cmindtest.presentation.news.NewsActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector


@Subcomponent(modules = arrayOf(NewsFragmentModule::class, ArticlesFragmentModule::class))
interface NewsActivitySubcomponent : AndroidInjector<NewsActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<NewsActivity>()
}