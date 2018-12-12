package com.bbrustol.cmindtest.di

import com.bbrustol.cmindtest.data.repository.NewsRepository
import com.bbrustol.cmindtest.data.source.NewsApi
import com.bbrustol.cmindtest.data.source.NewsApiImp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providesNewsRepository(newsApi: NewsApi): NewsRepository = NewsApiImp(newsApi)
}