package com.bbrustol.cmindtest.presentation.articles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.infrastruture.Constants
import com.bbrustol.cmindtest.infrastruture.replaceFragment
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

fun articlesActivity() = ArticlesActivity()

class ArticlesActivity : DaggerAppCompatActivity() {

    fun getLaunchingIntent(context: Context?, id: String): Intent {
        val extras = Bundle()
        extras.putString(Constants.ARGUMENT_ARTICLES_ID, id)

        val intent = Intent(context, ArticlesActivity::class.java)
        intent.putExtras(extras)

        return intent
    }

    private fun getArgumentID(): String {
        return intent.getStringExtra(Constants.ARGUMENT_ARTICLES_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)

        if (savedInstanceState == null) replaceFragment(R.id.framelayout_container, ArticlesFragment().newInstance(getArgumentID()), ARTICLES_FRAGMENT_TAG)
    }
}
