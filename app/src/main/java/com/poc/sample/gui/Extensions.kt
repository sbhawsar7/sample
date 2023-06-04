package com.poc.sample.gui

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.poc.sample.R
import com.poc.sample.base.domain.exception.Failure
import com.poc.sample.base.gui.AppToast
import timber.log.Timber
import java.util.regex.Pattern

class WebServiceFailure {
    class NoNetworkFailure(
        msg: String = "Network not available!"
    ) : Failure.DataFailure(msg)

    class AuthenticationFailure(
        msg: String = "Authentication error!"
    ) : Failure.DataFailure(msg)

    class NetworkTimeOutFailure(
        msg: String = "Network timeout!"
    ) : Failure.DataFailure(msg)

    class NetworkDataFailure(
        msg: String = "Error parsing data!"
    ) : Failure.DataFailure(msg)

    class UnknownNetworkFailure(
        msg: String = "Unknown network error!"
    ) : Failure.DataFailure(msg)
}

// TODO - reduce lot of boilerplate
internal fun Fragment.showErrorToast(msg: String) {
    AppToast.show(requireContext(), msg, Toast.LENGTH_SHORT)
}

fun Fragment.handleFailure(e: Exception?) {
    Timber.v("handleFailure: IN")
    Timber.e(e)
    when (e) {
        is WebServiceFailure.NoNetworkFailure -> showErrorToast("No internet connection!")
        is WebServiceFailure.NetworkTimeOutFailure, is WebServiceFailure.NetworkDataFailure -> showErrorToast("Internal server error!")
        else -> showErrorToast("Unknown error occurred!")
    }
    Timber.v("handleFailure: OUT")
}

internal fun Activity.showErrorToast(msg: String) {
    AppToast.show(applicationContext, msg, Toast.LENGTH_SHORT)
}

fun Activity.handleFailure(e: Exception?) {
    Timber.v("handleFailure: IN")
    Timber.e(e)
    when (e) {
        is WebServiceFailure.NoNetworkFailure -> showErrorToast("No internet connection!")
        is WebServiceFailure.NetworkTimeOutFailure, is WebServiceFailure.NetworkDataFailure -> showErrorToast("Internal server error!")
        else -> showErrorToast("Unknown error occurred!")
    }
    Timber.v("handleFailure: OUT")
}

fun Activity.animateToFade() {
    overridePendingTransition(R.anim.animate_fade_enter, R.anim.animate_fade_exit)
}

@BindingAdapter("visibleIf")
fun View.showLoader(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}