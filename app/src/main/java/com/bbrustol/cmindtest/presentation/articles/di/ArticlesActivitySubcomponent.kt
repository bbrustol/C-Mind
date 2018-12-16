package com.bbrustol.cmindtest.presentation.articles.di

import com.bbrustol.cmindtest.presentation.articles.ArticlesActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector


@Subcomponent(modules = arrayOf(ArticlesFragmentModule::class))
interface ArticlesActivitySubcomponent : AndroidInjector<ArticlesActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ArticlesActivity>()
}