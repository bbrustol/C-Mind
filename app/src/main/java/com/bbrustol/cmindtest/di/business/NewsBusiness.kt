package com.bbrustol.cmindtest.di.business

import com.bbrustol.cmindtest.data.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class NewsBusiness: BaseBusiness() {
    @Provides
    fun providesNewsApi(retrofit: Retrofit) = retrofit.create(ApiService::class.java)
}
