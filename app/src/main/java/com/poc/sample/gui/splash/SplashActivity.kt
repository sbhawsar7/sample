package com.poc.sample.gui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.poc.base.gui.activity.BaseActivity
import com.poc.sample.R
import com.poc.sample.databinding.ActivitySplashBinding
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.poc.sample.base.gui.failure
import com.poc.sample.base.gui.observe
import com.poc.sample.gui.*
import com.poc.sample.gui.login.LoginActivity
import com.poc.sample.gui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    override val layoutResId: Int get() = R.layout.activity_splash
    override val bindingInflater: (LayoutInflater) -> ViewDataBinding get() = ActivitySplashBinding::inflate
    override val binding: ActivitySplashBinding get() = super.binding as ActivitySplashBinding

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.apply {
            failure(failure, ::handleFailure)
            observe(isUserLoggedIn, ::moveToNextScreen)
            delayAndNavigateToLogin(sharedPreferences)
        }
    }

    private fun moveToNextScreen(isUserLoggedIn: Boolean) {
        Timber.d("moveToNextScreen: isUserLoggedIn=$isUserLoggedIn")
        val destinationActivity = when (isUserLoggedIn) {
            true -> MainActivity::class.java
            else -> LoginActivity::class.java
        }
        startActivity(Intent(applicationContext, destinationActivity))
        animateToFade()
        finish()
    }
}