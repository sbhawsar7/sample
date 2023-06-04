package com.poc.sample.network.model.request

import com.google.gson.annotations.SerializedName


data class LoginRequest(
    @SerializedName("username")
    var userName: String,
    @SerializedName("password")
    var password: String,
    @Transient var isUserNameValid: Boolean
) {
    @Transient var isPasswordValid: Boolean = false

    constructor() : this("", "", false)
}