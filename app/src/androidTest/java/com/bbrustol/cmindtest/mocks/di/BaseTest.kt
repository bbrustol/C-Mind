package com.bbrustol.cmindtest.mocks.di

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
abstract class BaseTest {

    @Rule
    @JvmField
    val daggerTestRule: DaggerTestRule<BaseTest, *> = addDaggerTestRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactoryMock

    protected fun addDaggerTestRule(): DaggerTestRule<BaseTest, *> =
        ViewModelFactoryMockRule(this)
}