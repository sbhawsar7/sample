package com.poc.sample.network.model

import com.google.gson.annotations.SerializedName
import com.poc.sample.UserStatus

const val HTTP_SUCCESS_CODE = "Success"

sealed class PilgrimResponse<T> {
    abstract fun isSuccessful(): Boolean
}

open class ApiResponse<T>(
    @SerializedName("data")
    var data: T,

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
) : PilgrimResponse<T>() {
    override fun isSuccessful(): Boolean {
        return data != null || code == HTTP_SUCCESS_CODE
    }

    fun hasNextPage(): Boolean {
        return if (isPaginated == null || isPaginated == false) {
            false
        } else {
            isLast == false
        }
    }
}