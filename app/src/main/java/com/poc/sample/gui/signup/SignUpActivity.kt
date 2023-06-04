package com.poc.sample.gui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.poc.base.gui.activity.BaseActivity
import com.poc.sample.R
import com.poc.sample.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity() {
    override val layoutResId: Int get() = R.layout.activity_sign_up
    override val bindingInflater: (LayoutInflater) -> ViewDataBinding get() = ActivitySignUpBinding::inflate
    override val binding: ActivitySignUpBinding get() = super.binding as ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}