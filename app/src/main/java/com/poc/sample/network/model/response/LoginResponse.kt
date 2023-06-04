package com.poc.sample.network.model.response

import com.google.gson.annotations.SerializedName
import com.poc.sample.UserStatus
import com.poc.sample.network.model.entity.User

data class LoginResponse(
    @SerializedName("data")
    var data: User,

    @SerializedName("isPaginated")
    private var isPaginated: Boolean?,

    @SerializedName("isLast")
    private var isLast: Boolean?,

    @SerializedName("message")
    var message: String,

    @SerializedName("user_status")
    var userStatus: String? = UserStatus.NONE.name,

    @SerializedName("code")
    var code: String
)