package com.bbrustol.cmindtest.di.business

import com.bbrustol.cmindtest.data.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ProvidesFactory: BaseFactory() {
    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit) = retrofit.create(ApiService::class.java)
}
