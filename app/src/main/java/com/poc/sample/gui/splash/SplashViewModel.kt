package com.poc.sample.gui.splash

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.poc.sample.SharedPreferenceConstants
import com.poc.sample.UserStatus
import com.poc.sample.base.getValue
import com.poc.sample.base.gui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): BaseViewModel() {

    companion object {
        const val MIN_DELAY = 1500L
    }

    private val _isUserLoggedIn: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    internal val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn

    fun delayAndNavigateToLogin(sharedPreferences: SharedPreferences) {
        launchDefaultCoroutine {
            delay(MIN_DELAY)

            val isUserStatusPending = sharedPreferences.getValue(SharedPreferenceConstants.USER_STATUS, "").equals(UserStatus.PENDING)
            if (sharedPreferences.getValue(SharedPreferenceConstants.IS_LOGIN, false))
                _isUserLoggedIn.postValue(true)
            else _isUserLoggedIn.postValue(false)
        }
    }
}