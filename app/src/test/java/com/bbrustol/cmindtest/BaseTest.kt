package com.bbrustol.cmindtest

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.lang.reflect.Type

/**
 * Unit test for [BaseTest].
 */
open class BaseTest {

    protected fun <T> getMockJson(jsonFile: String, clazz: Class<T>): T? {
        val file = readFile(jsonFile)

        return if (file != null) Gson().fromJson(file, clazz) as T else null
    }

    protected fun <T> getMockJson(jsonFile: String, type: Type): T? {
        val file = readFile(jsonFile)

        return if (file != null) Gson().fromJson<Any>(file, type) as T else null
    }

    private fun readFile(file: String): Reader {
        val inputStream = this.javaClass.classLoader.getResourceAsStream(file)

        return BufferedReader(InputStreamReader(inputStream))
    }
}