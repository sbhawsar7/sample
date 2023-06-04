package com.poc.base.gui.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.preference.PreferenceManager
import com.poc.base.displayName
import com.poc.sample.BR
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutResId: Int                                                         // Override it and initialise in child class
    protected abstract val bindingInflater: (LayoutInflater) -> ViewDataBinding                     // Override it and initialise in child class

    private var _binding: ViewDataBinding? = null

    protected open val binding get() = _binding!!
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.v("onCreate $displayName")
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Timber.v("onDestroy $displayName")
    }
}