package com.bbrustol.cmindtest.infrastruture

import java.util.*

class Constants private constructor() {
    companion object {
        const val INSTANTIATING_NOT_ALLOWED = "Instantiating not allowed"
        const val ARGUMENT_WEBVIEW_URL = "ARGUMENT_WEBVIEW_URL"
        const val ARGUMENT_ARTICLE_ID = "ARGUMENT_ARTICLE_ID"
    }

    class AppLocale private constructor() {
        init {
            throw ExceptionInInitializerError(INSTANTIATING_NOT_ALLOWED)
        }

        companion object {
            val BR_APP_LOCALE = Locale("pt", "BR")
            val US_APP_LOCALE = Locale("en", "US")
        }
    }
}