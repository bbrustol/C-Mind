package com.bbrustol.cmindtest.di;

import com.bbrustol.cmindtest.presentation.news.NewsActivity;
import com.bbrustol.cmindtest.presentation.news.di.NewsActivityModule;
import com.bbrustol.cmindtest.presentation.news.di.NewsFragmentProvider;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = {NewsActivityModule.class, NewsFragmentProvider.class})
    abstract NewsActivity bindNewsActivity();
}
