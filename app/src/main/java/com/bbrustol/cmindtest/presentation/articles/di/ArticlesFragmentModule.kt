package com.bbrustol.cmindtest.presentation.articles.di

import android.support.v4.app.Fragment
import com.bbrustol.cmindtest.presentation.articles.ArticlesFragment
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Subcomponent/*(modules = ...)*/
interface ArticlesFragmentSubcomponent: AndroidInjector<ArticlesFragment> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<ArticlesFragment>()
}

@Module(subcomponents = arrayOf(ArticlesFragmentSubcomponent::class))
abstract class ArticlesFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(ArticlesFragment::class)
    abstract fun bindArticlesFragmentInjectorFactory(builder: ArticlesFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>
}