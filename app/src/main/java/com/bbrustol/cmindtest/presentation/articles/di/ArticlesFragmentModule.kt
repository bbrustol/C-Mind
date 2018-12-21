package com.bbrustol.cmindtest.presentation.articles.di

import com.bbrustol.cmindtest.data.repository.ArticlesRepository
import com.bbrustol.cmindtest.infrastruture.SchedulerProvider
import com.bbrustol.cmindtest.presentation.articles.ArticlesViewModel
import dagger.Module
import dagger.Provides

@Module
class ArticlesFragmentModule{
    @Provides
    fun provideViewModel(repository: ArticlesRepository, schedulerProvider: SchedulerProvider) = ArticlesViewModel(repository, schedulerProvider)
}