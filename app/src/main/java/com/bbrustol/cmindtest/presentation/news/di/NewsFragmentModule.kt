package com.bbrustol.cmindtest.presentation.news.di

import com.bbrustol.cmindtest.data.repository.NewsRepository
import com.bbrustol.cmindtest.infrastruture.SchedulerProvider
import com.bbrustol.cmindtest.presentation.news.NewsViewModel
import dagger.Module
import dagger.Provides

@Module
class NewsFragmentModule{
    @Provides
    fun provideViewModel(repository: NewsRepository, schedulerProvider: SchedulerProvider) = NewsViewModel(repository, schedulerProvider)
}
