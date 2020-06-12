package com.maxproj.purebbs.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.maxproj.purebbs.R
import com.maxproj.purebbs.config.Config
import com.maxproj.purebbs.databinding.DetailFragmentBinding
import com.maxproj.purebbs.net.HttpService
import com.maxproj.purebbs.detail.DetailViewModel
import com.maxproj.purebbs.detail.DetailViewModelFactory
import kotlinx.android.synthetic.main.detail_fragment.*


class DetailFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this.requireActivity(), DetailViewModelFactory(this.requireActivity().application, HttpService.api))
            .get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("PureBBS","<detail> onCreateView")
        val binding: DetailFragmentBinding = bindingViewModelInit()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d("PureBBS","<detail> onResume")
        val safeArgs: DetailFragmentArgs by navArgs()
        viewModel.changePostId(safeArgs.postId)
    }

    private fun bindingViewModelInit(): DetailFragmentBinding {

        val binding: DetailFragmentBinding = DetailFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        viewModel.detailAdapter.viewModel = viewModel
        viewModel.detailAdapter.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.detailRecyclerview.adapter = viewModel.detailAdapter

        viewModel.detailList?.observe(viewLifecycleOwner, Observer {
            Log.d("PureBBS","<detail> DetailFragment viewModel.detailAdapter.submitList:$it")
            viewModel.detailAdapter.submitList(it)
        })
        return binding
    }
}
