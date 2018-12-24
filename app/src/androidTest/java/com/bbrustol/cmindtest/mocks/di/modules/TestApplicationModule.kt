package com.bbrustol.cmindtest.mocks.di.modules

import com.bbrustol.cmindtest.infrastruture.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class TestApplicationModule() {

//    @Provides
//    @Singleton
//    fun provideNewsViewModel(): NewsViewModel {
//        return newsViewModel
//    }

//    @Provides
//    @Singleton
//    fun provideArticlesRepository(): ArticlesRepository {
//        return articlesRepository
//    }

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())
}