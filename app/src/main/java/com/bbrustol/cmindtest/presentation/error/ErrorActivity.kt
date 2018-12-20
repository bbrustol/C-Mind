package com.bbrustol.cmindtest.presentation.error

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.infrastruture.Constants
import kotlinx.android.synthetic.main.activity_error.*
import kotlinx.android.synthetic.main.activity_webview.*

fun errorActivity() = ErrorActivity()

val ERROR_ACTIVITY_TAG = ErrorActivity::class.java.name

class ErrorActivity : AppCompatActivity() {

    fun getLaunchingIntent(
        context: Context?,
        imageRes: Int,
        textRes: Int): Intent {
        val extras = Bundle()
        extras.putInt(Constants.Error.ARGUMENT_IMAGE_RES, imageRes)
        extras.putInt(Constants.Error.ARGUMENT_TEXT_RES, textRes)

        val intent = Intent(context, ErrorActivity::class.java)
        intent.putExtras(extras)

        return intent
    }

    //region private methods
    private fun getArguments(argument: String): Int {
        return intent.getIntExtra(argument, 0)
    }

    //endregion

    //region override methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)
        setup()
    }

    private fun setup() {
        error_image.setImageResource(getArguments(Constants.Error.ARGUMENT_IMAGE_RES))
        error_text.setText(getArguments(Constants.Error.ARGUMENT_TEXT_RES))
        error_button.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        try {
            webView.webViewClient = null
        }catch (e: Exception) {
            Log.e(ERROR_ACTIVITY_TAG, e.message)
        }
    }
    //endregion
}
