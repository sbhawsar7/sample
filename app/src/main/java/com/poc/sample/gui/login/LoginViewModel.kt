package com.poc.sample.gui.login

import androidx.annotation.StringRes
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.poc.sample.MyApplication
import com.poc.sample.R
import com.poc.sample.base.gui.viewmodel.BaseViewModel
import com.poc.sample.base.platform.ActionLiveData
import com.poc.sample.network.model.entity.User
import com.poc.sample.network.model.request.LoginRequest
import com.poc.sample.network.remote_repositories.LoginRepository
import com.poc.sample.network.result.APIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jetbrains.annotations.ApiStatus
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :BaseViewModel() {

    private var loginRequestModel = LoginRequest("","", false)

    private var _signUpBtnClick = MutableLiveData<Boolean>()
    private var _forgotPasswordBtnClick = MutableLiveData<Boolean>()
    private var _loginResult = MutableLiveData<Pair<Boolean, User?>>()

    var btnLoginEnable = ObservableField(false)
    var loginResult :LiveData<Pair<Boolean, User?>> = _loginResult
    var onForgotPasswordBtnClick:LiveData<Boolean> = _forgotPasswordBtnClick
    var onSignUpBtnClick:LiveData<Boolean> = _signUpBtnClick
    var loadedVisibility = ObservableField(false)

    var errorAction = ActionLiveData<Pair<LoginField, String?>>()

    fun onForgotPasswordClick() {
        _forgotPasswordBtnClick.postValue(true)
    }

    fun onSignUpClick() {
        _signUpBtnClick.postValue(true)
    }

    fun onFocusChanged(loginField: LoginField, hasFocus: Boolean) {
        when (loginField) {
            LoginField.USER_NAME -> validateUserName(hasFocus)
            LoginField.PASSWORD -> validatePassword(hasFocus)
        }
        validateFormField()
    }

    fun afterTextChange(loginField: LoginField, value: String) {
        loginRequestModel.apply {
            when (loginField) {
                LoginField.USER_NAME -> validateUserName(value)
                LoginField.PASSWORD -> validatePassword(value)
            }
            validateFormField()
        }
    }

    fun onLoginClick(){
        if (loginRequestModel.isUserNameValid && loginRequestModel.isPasswordValid){
            loadedVisibility.set(true)
            launchDefaultCoroutine {
                val result = loginRepository.login(loginRequestModel)
                when(result){
                    is APIResult.Success-> {
                        _loginResult.postValue(Pair(true, result.value?.data))
                        loadedVisibility.set(false)
                    }
                    is APIResult.Failure-> {
                        loadedVisibility.set(false)
                        _loginResult.postValue(Pair(false, null))
                    }
                }
            }
        }
    }

    fun onLoginClick2(){
        if (loginRequestModel.isUserNameValid && loginRequestModel.isPasswordValid){
            loadedVisibility.set(true)
            launchDefaultCoroutine {
                loginRepository.login(loginRequestModel)
                { response, errors, exception ->
                    response?.let {
                        _loginResult.postValue(Pair(true, response))
                        launchIOCoroutine {
                            loginRepository.addUser(response)
                        }
                    }
                    errors?.let {
                        _loginResult.postValue(Pair(false, null))
                    }
                    exception?.let {
                        _loginResult.postValue(Pair(false, null))
                    }
                    loadedVisibility.set(false)
                }
            }
        }
    }

    private fun validateUserName(userName: String) {
        loginRequestModel.apply {
            this.userName = userName
            if (this.userName.length>=5) {
                isUserNameValid = true
                sendErrorAction(LoginField.USER_NAME, null)
            } else isUserNameValid = false
        }
    }

    private fun validatePassword(password: String) {
        loginRequestModel.apply {
            this.password = password
            if (password.length>=6) {
                isPasswordValid = true
                sendErrorAction(LoginField.PASSWORD, null)
            } else isPasswordValid = false
        }
    }

    private fun validateUserName(hasFocus: Boolean) {
        if (!hasFocus && !loginRequestModel.isUserNameValid && loginRequestModel.userName.isNotEmpty()) {
            sendErrorAction(LoginField.USER_NAME, R.string.username_invalid)
        } else if (!hasFocus && loginRequestModel.userName.isEmpty()) {
            sendErrorAction(LoginField.USER_NAME, R.string.username_empty_error)
        } else {
            sendErrorAction(LoginField.USER_NAME, null)
        }
    }

    private fun validatePassword(hasFocus: Boolean) {
        if (!hasFocus && !loginRequestModel.isPasswordValid && loginRequestModel.password.isNotEmpty()) {
            sendErrorAction(LoginField.PASSWORD, R.string.password_invalid)
        } else if (!hasFocus && loginRequestModel.password.isEmpty()) {
            sendErrorAction(LoginField.PASSWORD, R.string.password_empty_error)
        } else {
            sendErrorAction(LoginField.PASSWORD, null)
        }
    }

    fun validateFormField() {
        if (!loginRequestModel.isUserNameValid || loginRequestModel.password.isEmpty() || !loginRequestModel.isPasswordValid)
            btnLoginEnable.set(false)
        else
            btnLoginEnable.set(true)
    }

    private fun sendErrorAction(type: LoginField, @StringRes errorResId: Int?) {
        val errorMsg = if (errorResId != null) MyApplication.getContext().getString(errorResId) else null
        errorAction.sendAction(Pair(type, errorMsg))
    }
}

enum class LoginField {
    USER_NAME, PASSWORD
}