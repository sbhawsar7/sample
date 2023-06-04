package com.poc.sample.network.remote_repositories

import com.poc.sample.db.dao.UserDao
import com.poc.sample.network.model.entity.User
import com.poc.sample.network.model.request.ApiRequest
import com.poc.sample.network.model.request.LoginRequest
import com.poc.sample.network.model.response.LoginResponse
import com.poc.sample.network.services.AuthService
import retrofit2.Call
import retrofit2.Response

class LoginRepository(private val authService: AuthService, private val userDao: UserDao) {

    suspend fun login(loginRequestModel: LoginRequest) = authService.login(ApiRequest(loginRequestModel))

    fun login(loginRequestModel: LoginRequest, onResult: (response: User?, errors : String?, exception: Throwable?) -> Unit) {
        authService.login2(ApiRequest(loginRequestModel))
            .enqueue(object :retrofit2.Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful && response.body()!=null && response.body()?.data!=null)
                        onResult(response.body()?.data, null, null)
                    else if (!response.isSuccessful)
                        onResult(null, response.message(), null)
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    onResult(null,null, t)
                }
            })
    }

    fun addUser(user: User){
        userDao.add(user)
    }
}