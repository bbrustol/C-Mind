package com.bbrustol.cmindtest.presentation

import android.support.v4.app.Fragment
import android.view.View
import kotlinx.android.synthetic.main.include_shimmer.*

open class BaseFragment : Fragment() {

    fun showShimmer (flag: Boolean) {
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
