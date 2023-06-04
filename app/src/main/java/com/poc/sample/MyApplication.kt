package com.poc.sample

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Message
import android.preference.PreferenceManager
import com.poc.base.displayName
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import android.widget.Toast

@HiltAndroidApp
class MyApplication:Application() {

    companion object {
        lateinit var instance: MyApplication

        fun getContext(): Context {
            return instance
        }

        fun showToastMessage(activity: Activity, message: String){
            activity.runOnUiThread(Runnable {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            })
        }
    }

    init {
        instance = this
        Timber.plant(Timber.DebugTree())
    }

    override fun onCreate() {
        super.onCreate()
        Timber.v("onCreate $displayName")
    }
}