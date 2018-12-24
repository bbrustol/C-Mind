package com.bbrustol.cmindtest.presentation.articles.di;

import com.bbrustol.cmindtest.presentation.articles.ArticlesFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ArticlesFragmentProvider {
    @ContributesAndroidInjector(modules = ArticlesFragmentModule.class)
    abstract ArticlesFragment provideArticlesFragmentFactory();
}
