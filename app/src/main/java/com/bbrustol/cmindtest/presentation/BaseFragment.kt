package com.bbrustol.cmindtest.presentation

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.support.v4.app.Fragment
import android.view.View
import com.bbrustol.cmindtest.R
import com.bbrustol.cmindtest.infrastruture.Constants
import com.bbrustol.cmindtest.presentation.error.errorActivity
import com.bbrustol.cmindtest.presentation.webview.webviewActivity
import kotlinx.android.synthetic.main.include_shimmer.*

open class BaseFragment : Fragment() {
    fun showShimmer (flag: Boolean) {
        if (shimmer_view_container != null) {
            shimmer_view_container.apply {
                if (flag) {
                    startShimmerAnimation()
                    visibility = View.VISIBLE
                } else {
                    stopShimmerAnimation()
                    visibility = View.GONE
                }
            }
        }
    }

    fun openWebview(url: String, title: String = "") {
        if (checkConnection()) {
            startActivity(webviewActivity().getLaunchingIntent(context, url, title))
        }else {
            internetError(Constants.Error.ARGUMENT_CONNECTION_ERROR)
        }
    }

    fun internetError(requestCode:Int) {
        startActivityForResult(errorActivity().getLaunchingIntent(
            context,
            R.drawable.signal_wifi_off,
            R.string.check_your_connection_and_try_again),
        requestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.Error.ARGUMENT_CONNECTION_ERROR) {
        }
    }


    @Suppress("DEPRECATION")
    fun checkConnection(): Boolean {
        if (context == null)
            return false
        val manager = context!!
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var info: NetworkInfo?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val nets = manager.allNetworks
            for (net in nets) {
                info = manager.getNetworkInfo(net)
                if (info != null && info.state == NetworkInfo.State.CONNECTED)
                    return true
            }
            return false
        } else {
            info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if (info != null && info.state == NetworkInfo.State.CONNECTED)
                return true
            info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (info != null && info.state == NetworkInfo.State.CONNECTED)
                return true
            info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX)
            return info != null && info.state == NetworkInfo.State.CONNECTED
        }
    }
}
