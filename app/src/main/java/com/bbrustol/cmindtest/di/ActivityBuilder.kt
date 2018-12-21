package com.bbrustol.cmindtest.di

import com.bbrustol.cmindtest.presentation.articles.ArticlesActivity
import com.bbrustol.cmindtest.presentation.articles.di.ArticlesActivityModule
import com.bbrustol.cmindtest.presentation.articles.di.ArticlesFragmentProvider
import com.bbrustol.cmindtest.presentation.news.NewsActivity
import com.bbrustol.cmindtest.presentation.news.di.NewsActivityModule
import com.bbrustol.cmindtest.presentation.news.di.NewsFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = arrayOf(NewsActivityModule::class, NewsFragmentProvider::class))
    internal abstract fun bindNewsActivity(): NewsActivity

    @ContributesAndroidInjector(modules = arrayOf(ArticlesActivityModule::class, ArticlesFragmentProvider::class))
    internal abstract fun bindArticlesActivity(): ArticlesActivity
}
