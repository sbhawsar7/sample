package com.poc.sample.network.model.request

import androidx.preference.PreferenceManager
import com.poc.sample.MyApplication
import com.poc.sample.SharedPreferenceConstants
import com.poc.sample.base.getValue
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class ApiRequest<T>(
    var data: T
)

@JsonClass(generateAdapter = true)
class AuthHeader(
    var token: String,
    var userId: String,
    var username: String
) {
    fun toHeaderMap() =
        mapOf(Pair("x-auth", token), Pair("user_id", userId), Pair("username", username))
}

fun getApplicationHeaders(
    token: String? = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).getValue(SharedPreferenceConstants.TOKEN, ""),
    userId: String? = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).getValue(SharedPreferenceConstants.USER_ID, "")
): AuthHeader {
    // Assigned default userid in case of there is no userid
    val userID = if (userId.isNullOrEmpty()) "abc" else userId
    return AuthHeader(token ?: "", userID, userID)
}
