package com.bbrustol.cmindtest.presentation.articles.di

import android.app.Activity
import com.bbrustol.cmindtest.presentation.articles.ArticlesActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [ArticlesActivitySubcomponent::class])
abstract class ArticlesActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(ArticlesActivity::class)
    abstract fun bindArticlesActivityInjectorFactory(builder: ArticlesActivitySubcomponent.Builder): AndroidInjector.Factory<out Activity>
}