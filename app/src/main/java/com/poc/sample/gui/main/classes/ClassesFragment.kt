package com.poc.sample.gui.main.classes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.poc.sample.R
import com.poc.sample.base.gui.failure
import com.poc.sample.base.gui.observe
import com.poc.sample.databinding.FragmentClassesBinding
import com.poc.sample.gui.handleFailure
import com.sensibol.android.base.gui.fragment.BaseFragment

class ClassesFragment : BaseFragment() {
    override val layoutResId: Int get() = R.layout.fragment_classes
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewDataBinding get() = FragmentClassesBinding::inflate
    override val binding: FragmentClassesBinding get() = super.binding as FragmentClassesBinding

    private val viewModel: ClassesViewModel by viewModels()

    override fun onInitView() {
        binding.viewModel = viewModel
        viewModel.apply {
            failure(failure, ::handleFailure)
        }
    }

}