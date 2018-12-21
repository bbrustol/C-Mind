package com.bbrustol.cmindtest.presentation.news.di;

import com.bbrustol.cmindtest.presentation.news.NewsFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NewsFragmentProvider {

    @ContributesAndroidInjector(modules = NewsFragmentModule.class)
    abstract NewsFragment provideNewsFragmentFactory();
}
