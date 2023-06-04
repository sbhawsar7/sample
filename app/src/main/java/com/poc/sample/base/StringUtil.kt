package com.poc.sample.base

import android.content.res.Resources
import android.os.Build
import androidx.annotation.StringRes
import com.poc.sample.MyApplication
import com.poc.sample.R
import java.util.*

object StringUtil {
    @JvmStatic
    fun getString(@StringRes messageId: Int, format: String? = null): String {

        var res: Resources = MyApplication.getContext().resources
        val conf = res.configuration
        if (true) {
            conf.setLocale(Locale("en"))
        } else {
            conf.setLocale(Locale.getDefault())
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            res = MyApplication.getContext().createConfigurationContext(conf).resources
        } else {
            res.updateConfiguration(conf, null)
        }
        val stringValue = if (format == null) res.getString(messageId)
        else res.getString(messageId, format)
        return stringValue
    }
}

fun readFileFromAssets(fileName: String): String {
    val result = MyApplication.getContext().assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return result
}

fun getStringResourceByName(aString: String): String {
    val packageName = MyApplication.getContext().packageName
    val resId = MyApplication.getContext().resources
        .getIdentifier(aString, "string", packageName)
    return if (resId == 0) StringUtil.getString(R.string.key_not_found, aString)
    else StringUtil.getString(resId)
}
