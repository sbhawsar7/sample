package com.poc.sample.network.result

import com.poc.sample.R
import com.poc.sample.base.StringUtil.getString
import com.poc.sample.network.model.ApiResponse
import com.poc.sample.network.model.HTTP_SUCCESS_CODE

sealed class APIResult<T> {
    class None<T>() : APIResult<T>()
    class Failure<T>(val details: APIError) : APIResult<T>()
    class Success<T>(val response: ApiResponse<T>) : APIResult<T>() {
        constructor(value: T) : this(ApiResponse(value, null, null, "", "", HTTP_SUCCESS_CODE))
    }

    val value: ApiResponse<T>?
        get() = (this as? Success)?.response

    val error: APIError?
        get() = (this as? Failure)?.details

    fun hasNextPage(): Boolean {
        return when (this) {
            is Success -> this.response.hasNextPage()
            is Failure -> false
            else -> false
        }
    }

    fun <U> map(apply: (T) -> U): APIResult<U> {
        return when (this) {
            is Success -> {
                val data = this.response.data
                val transformed = apply(data)
                Success(
                    ApiResponse(
                        transformed, this.response.hasNextPage(), !this.response.hasNextPage(),
                        this.response.message, this.response.userStatus, this.response.code
                    )
                )
            }
            is Failure -> {
                Failure(this.details)
            }
            else -> None()
        }
    }

    fun withFailure(failure: (APIError) -> Unit): APIResult<T> {
        if (this is Failure) {
            failure(this.details)
        }
        return this
    }
}

sealed class APIErrorType {
    object General : APIErrorType()
    object SessionExpired : APIErrorType()
    object CallNotTried : APIErrorType()
    object InvalidResponse : APIErrorType()
    object NoVideoAvailable : APIErrorType()
    object PilgrimMock : APIErrorType()
    object Purchase : APIErrorType()
    object SignUpError : APIErrorType()
    object userDeactiveted : APIErrorType()
    class Network(val throwable: Throwable) : APIErrorType()
    object UserDeleted : APIErrorType()
    object Orphan : APIErrorType()
    object UserDeactivated : APIErrorType()
}

data class APIError(
    val errors: Map<String, String>,
    val type: APIErrorType,
    val code: Int,
    val thrownCause: Throwable?,
    override val message: String
) : Exception(message) {
    companion object {
        val notTried = APIError(
            hashMapOf(), APIErrorType.CallNotTried, -1, null, getString(R.string.not_implemented)
        )
        val wrongJsonResponse = APIError(
            hashMapOf(), APIErrorType.InvalidResponse, -1, null,
            getString(R.string.invalid_response)
        )
        val notImplemented = APIError(
            hashMapOf(), APIErrorType.General, -1, null, getString(R.string.not_implemented)
        )
        val default = APIError(
            hashMapOf(), APIErrorType.General, -1, null, getString(R.string.default_api_error)
        )
    }
}
