package com.bbrustol.cmindtest.di.business

import com.bbrustol.cmindtest.data.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ArticlesBusiness: BaseFactory() {
    @Provides
    @Singleton
    @Named("articles")
    fun providesArticlesApi(retrofit: Retrofit) = retrofit.create(ApiService::class.java)
}
