package com.bbrustol.cmindtest.presentation.news.di

import android.app.Activity
import com.bbrustol.cmindtest.presentation.news.NewsActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [NewsActivitySubcomponent::class])
abstract class NewsActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(NewsActivity::class)
    abstract fun bindNewsActivityInjectorFactory(builder: NewsActivitySubcomponent.Builder): AndroidInjector.Factory<out Activity>
}