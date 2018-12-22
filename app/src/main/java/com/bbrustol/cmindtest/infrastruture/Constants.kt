package com.bbrustol.cmindtest.infrastruture

class Constants private constructor() {
    companion object {
        const val INSTANTIATING_NOT_ALLOWED = "Instantiating not allowed"
        const val ARGUMENT_WEBVIEW_URL = "ARGUMENT_WEBVIEW_URL"
        const val ARGUMENT_WEBVIEW_NAME = "ARGUMENT_WEBVIEW_NAME"
        const val ARGUMENT_ARTICLES_ID = "ARGUMENT_ARTICLES_ID"
        const val ARGUMENT_ARTICLES_PAGE = "ARGUMENT_ARTICLES_PAGE"
    }

    class Error private constructor() {
        init {
            throw ExceptionInInitializerError(INSTANTIATING_NOT_ALLOWED)
        }

        companion object {
            const val ARGUMENT_CONNECTION_ERROR: Int = 1
            const val ARGUMENT_API_ERROR: Int = 2
            const val ARGUMENT_IMAGE_RES = "ARGUMENT_IMAGE_RES"
            const val ARGUMENT_TEXT_RES = "ARGUMENT_TEXT_RES"
        }
    }
}