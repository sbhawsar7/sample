package com.poc.sample.gui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.poc.base.gui.activity.BaseActivity
import com.poc.sample.MyApplication
import com.poc.sample.R
import com.poc.sample.RequestCodes
import com.poc.sample.SharedPreferenceConstants
import com.poc.sample.base.gui.failure
import com.poc.sample.base.gui.observe
import com.poc.sample.base.setValue
import com.poc.sample.databinding.ActivityLoginBinding
import com.poc.sample.gui.*
import com.poc.sample.gui.forgot_password.ForgotPasswordActivity
import com.poc.sample.gui.main.MainActivity
import com.poc.sample.gui.signup.SignUpActivity
import com.poc.sample.network.model.entity.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    override val layoutResId: Int get() = R.layout.activity_login
    override val bindingInflater: (LayoutInflater) -> ViewDataBinding get() = ActivityLoginBinding::inflate
    override val binding: ActivityLoginBinding get() = super.binding as ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.apply {
            failure(failure, ::handleFailure)
            observe(onSignUpBtnClick, ::moveToRegistrationPage)
            observe(onForgotPasswordBtnClick, ::moveToForgotPasswordPage)
            observe(errorAction, ::onLoginFormError)
            observe(loginResult, ::onPressLoginButton)
        }
    }

    private fun moveToRegistrationPage(shouldMove: Boolean) {
        if (shouldMove) startActivityForResult(Intent(applicationContext, SignUpActivity::class.java), RequestCodes.REGISTRATION_RC)
    }

    private fun moveToForgotPasswordPage(shouldMove: Boolean) {
        if (shouldMove) startActivity(Intent(applicationContext, ForgotPasswordActivity::class.java))
    }

    private fun onLoginFormError(pair: Pair<LoginField, String?>?) {
        when (pair?.first) {
            LoginField.USER_NAME -> binding.tiUserName.error = pair.second
            LoginField.PASSWORD -> binding.tiPassword.error = pair.second
        }
    }

    private fun onPressLoginButton(pair: Pair<Boolean, User?>) {
        when (pair.first) {
            true -> {
                sharedPreferences.setValue(SharedPreferenceConstants.IS_LOGIN, true)
                sharedPreferences.setValue(SharedPreferenceConstants.USER_ID, pair.second?.userId)
                sharedPreferences.setValue(SharedPreferenceConstants.TOKEN, pair.second?.token)
                sharedPreferences.setValue(SharedPreferenceConstants.USER_STATUS, pair.second?.status)
                sharedPreferences.setValue(SharedPreferenceConstants.NAME, pair.second?.fullName)
                startActivity(Intent(applicationContext, MainActivity::class.java))
                animateToFade()
                finish()}
            false -> {
                MyApplication.showToastMessage(this, "Invalid UserName or Password")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestCodes.REGISTRATION_RC)
            clearLoginData()
    }

    private fun clearLoginData() {
        binding.apply {
            tiUserName.error = null
            tiPassword.error = null
            etEmail.setText("")
            etPassword.setText("")
        }
    }
}