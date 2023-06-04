package com.poc.sample.gui.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.poc.base.gui.activity.BaseActivity
import com.poc.sample.R
import com.poc.sample.databinding.ActivityForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : BaseActivity() {
    override val layoutResId: Int get() = R.layout.activity_forgot_password
    override val bindingInflater: (LayoutInflater) -> ViewDataBinding get() = ActivityForgotPasswordBinding::inflate
    override val binding: ActivityForgotPasswordBinding get() = super.binding as ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}