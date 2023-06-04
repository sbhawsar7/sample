package com.poc.sample.network.services

import com.poc.sample.network.model.entity.User
import com.poc.sample.network.model.request.ApiRequest
import com.poc.sample.network.model.request.LoginRequest
import com.poc.sample.network.model.response.LoginResponse
import com.poc.sample.network.result.APIResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AuthService {

    @POST("app/user/login")
    suspend fun login(@Body login: ApiRequest<LoginRequest>): APIResult<User>

    @POST("app/user/login")
    fun login2(@Body login: ApiRequest<LoginRequest>): Call<LoginResponse>

}
