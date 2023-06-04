package com.poc.sample.gui.main.sermon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.poc.sample.R
import com.poc.sample.base.gui.failure
import com.poc.sample.base.gui.observe
import com.poc.sample.databinding.FragmentClassesBinding
import com.poc.sample.databinding.FragmentGospelBinding
import com.poc.sample.databinding.FragmentSermonBinding
import com.poc.sample.gui.handleFailure
import com.sensibol.android.base.gui.fragment.BaseFragment

class SermonFragment : BaseFragment() {
    override val layoutResId: Int get() = R.layout.fragment_sermon
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewDataBinding get() = FragmentSermonBinding::inflate
    override val binding: FragmentSermonBinding get() = super.binding as FragmentSermonBinding

    private val viewModel: SermonViewModel by viewModels()

    override fun onInitView() {
        binding.viewModel = viewModel
        viewModel.apply {
            failure(failure, ::handleFailure)
        }
    }

}