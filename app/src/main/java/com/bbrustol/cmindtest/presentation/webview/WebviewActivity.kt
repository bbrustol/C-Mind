package com.bbrustol.cmindtest.presentation.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.infrastruture.Constants
import com.bbrustol.cmindtest.infrastruture.replaceFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class WebviewActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private val webviewFragment by lazy { webviewFragment() }

    fun getLaunchingIntent(context: Context, url: String): Intent {
        val extras = Bundle()
        extras.putString(Constants.ARGUMENT_WEBVIEW_URL, url)

        val intent = Intent(context, WebviewActivity::class.java)
        intent.putExtras(extras)

        return intent
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        if (savedInstanceState == null) replaceFragment(R.id.framelayout_container, webviewFragment, WEBVIEW_FRAGMENT_TAG)
    }
}
