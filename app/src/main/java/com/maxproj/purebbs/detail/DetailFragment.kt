package com.maxproj.purebbs.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.maxproj.purebbs.config.SharedViewModel
import com.maxproj.purebbs.databinding.DetailFragmentBinding
import com.maxproj.purebbs.net.HttpService


class DetailFragment : Fragment() {

    private val viewModel by lazy {
        Log.d("PureBBS","<detail> viewModel by lazy")
        ViewModelProvider(this, DetailViewModelFactory(this.requireActivity().application, HttpService.api))
            .get(DetailViewModel::class.java)
    }

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("PureBBS", "<lifecycle> DetailFragment onCreateView")

//        val safeArgs: DetailFragmentArgs by navArgs()
//        postId = safeArgs.postId

        val binding: DetailFragmentBinding = bindingViewModelInit()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
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

        Log.d("PureBBS","<detail> detailList?.observe")
        viewModel.detailList?.observe(viewLifecycleOwner, Observer {

            Log.d("PureBBS","<detail> DetailFragment viewModel.sharedViewModel:${sharedViewModel.post}")

            Log.d("PureBBS","<detail> DetailFragment viewModel.detailAdapter.submitList:$it")
            viewModel.detailAdapter.submitList(it)
        })

//        viewModel.sharedViewModel = sharedViewModel

        return binding
    }
}
