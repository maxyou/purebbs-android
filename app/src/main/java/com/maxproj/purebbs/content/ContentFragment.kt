package com.maxproj.purebbs.content

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.maxproj.purebbs.R
import com.maxproj.purebbs.config.Config
import com.maxproj.purebbs.config.SharedViewModel
import com.maxproj.purebbs.databinding.ContentFragmentBinding
import com.maxproj.purebbs.databinding.PostFragmentBinding
import com.maxproj.purebbs.net.HttpService
import com.maxproj.purebbs.post.PostViewModel
import com.maxproj.purebbs.post.PostViewModelFactory

class ContentFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this, ContentViewModelFactory(this.requireActivity().application, HttpService.api))
            .get(ContentViewModel::class.java)
    }

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: ContentFragmentBinding = bindingViewModelInit()

        return binding.root
    }

    private fun bindingViewModelInit(): ContentFragmentBinding {

        val binding: ContentFragmentBinding = ContentFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.text.text = sharedViewModel.post?.content

        return binding
    }
}