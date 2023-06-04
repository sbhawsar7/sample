package com.poc.sample.gui.main.gospel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.poc.sample.R
import com.poc.sample.base.gui.failure
import com.poc.sample.base.gui.observe
import com.poc.sample.databinding.FragmentClassesBinding
import com.poc.sample.databinding.FragmentGospelBinding
import com.poc.sample.gui.handleFailure
import com.sensibol.android.base.gui.fragment.BaseFragment

class GospelFragment : BaseFragment() {
    override val layoutResId: Int get() = R.layout.fragment_gospel
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewDataBinding get() = FragmentGospelBinding::inflate
    override val binding: FragmentGospelBinding get() = super.binding as FragmentGospelBinding

    private val viewModel: GospelViewModel by viewModels()

    override fun onInitView() {
        binding.viewModel = viewModel
        viewModel.apply {
            failure(failure, ::handleFailure)
        }
    }

}