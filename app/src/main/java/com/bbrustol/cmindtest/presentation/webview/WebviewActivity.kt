package com.bbrustol.cmindtest.presentation.webview

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.infrastruture.Constants
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_webview.*


fun webviewActivity() = WebviewActivity()

class WebviewActivity : AppCompatActivity() {

    fun getLaunchingIntent(context: Context?, url: String): Intent {
        val extras = Bundle()
        extras.putString(Constants.ARGUMENT_WEBVIEW_URL, url)

        val intent = Intent(context, WebviewActivity::class.java)
        intent.putExtras(extras)

        return intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        init()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        webView.webViewClient = null
        finish()
    }

    private fun init() {
        toolbar.title = getString(R.string.app_name)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        startWebView()
    }

    private fun startWebView() {
        loading.visibility = View.VISIBLE

        webView.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY

        webView.settings.builtInZoomControls = true
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                loading.visibility = View.GONE
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(this@WebviewActivity, "Error: ${error.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        webView.loadUrl(getArgumentUrl())
    }

    fun getArgumentUrl(): String {
        return intent.getStringExtra(Constants.ARGUMENT_WEBVIEW_URL)
    }
}
