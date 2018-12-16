package com.bbrustol.cmindtest.di

import com.bbrustol.cmindtest.data.repository.ArticlesRepository
import com.bbrustol.cmindtest.data.repository.NewsRepository
import com.bbrustol.cmindtest.data.source.Api
import com.bbrustol.cmindtest.data.source.ArticlesApiImp
import com.bbrustol.cmindtest.data.source.NewsApiImp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providesNewsRepository(api: Api): NewsRepository = NewsApiImp(api)

    @Provides
    @Singleton
    fun providesArticlesRepository(api: Api): ArticlesRepository = ArticlesApiImp(api)
}