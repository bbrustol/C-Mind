package com.bbrustol.cmindtest.presentation.error

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.infrastruture.Constants
import kotlinx.android.synthetic.main.activity_webview.*

fun errorActivity() = ErrorActivity()

class ErrorActivity : AppCompatActivity() {

    fun getLaunchingIntent(context: Context?, url: String): Intent {
        val extras = Bundle()
        extras.putString(Constants.ARGUMENT_WEBVIEW_URL, url)

        val intent = Intent(context, ErrorActivity::class.java)
        intent.putExtras(extras)

        return intent
    }

    //region private methods
    private fun getArgumentUrl(): String {
        return intent.getStringExtra(Constants.ARGUMENT_WEBVIEW_URL)
    }

    //endregion

    //region override methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        webView.webViewClient = null
    }
    //endregion
}
