package com.bbrustol.cmindtest.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
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

    fun openWebview(url: String) {
        if (checkConnection()) {
            startActivity(webviewActivity().getLaunchingIntent(context, url))
        }else {
            Toast.makeText(context, "Fazer tela de erro", Toast.LENGTH_SHORT).show()
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
