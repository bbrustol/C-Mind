package com.bbrustol.cmindtest.di

import com.bbrustol.cmindtest.business.interectors.NewsInteractor
import com.bbrustol.cmindtest.data.model.NewsUseCases
import com.bbrustol.cmindtest.data.repository.NewsRepository
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun providesNewsUseCases(newsRepository: NewsRepository): NewsUseCases = NewsInteractor(newsRepository)
}