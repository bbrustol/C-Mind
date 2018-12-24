package com.bbrustol.cmindtest.testutil

import android.support.test.InstrumentationRegistry
import com.bbrustol.cmindtest.mocks.di.components.DaggerTestApplicationComponent
import com.bbrustol.cmindtest.mocks.di.modules.TestApplicationModule

class TestInjector(private val testApplicationModule: TestApplicationModule) {

    fun inject() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as TestApplication

        DaggerTestApplicationComponent
                .builder()
                .appModule(testApplicationModule)
                .create(app)
                .inject(app)
    }
}