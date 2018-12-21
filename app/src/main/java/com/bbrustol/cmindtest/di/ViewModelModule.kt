package com.bbrustol.cmindtest.di

import dagger.Module

//@Target(AnnotationTarget.FUNCTION)
//@Retention(AnnotationRetention.RUNTIME)
//@MapKey
//internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {
//    @Binds
//    @IntoMap
//    @ViewModelKey(NewsViewModel::class)
//    abstract fun bindNewsViewModel(viewModel: NewsViewModel) : ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ArticlesViewModel::class)
//    abstract fun bindArticlesViewModel(viewModel: ArticlesViewModel) : ViewModel
}