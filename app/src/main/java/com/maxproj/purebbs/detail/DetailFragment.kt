package com.maxproj.purebbs.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.maxproj.purebbs.databinding.DetailFragmentBinding
import com.maxproj.purebbs.net.HttpService


class DetailFragment : Fragment() {

//    lateinit var postId:String

    private val viewModel by lazy {
        Log.d("PureBBS","<detail> viewModel by lazy")
        ViewModelProvider(this, DetailViewModelFactory(this.requireActivity().application, HttpService.api))
            .get(DetailViewModel::class.java)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("PureBBS", "<lifecycle> DetailFragment onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("PureBBS", "<lifecycle> DetailFragment onDestroy")
    }
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
        viewModel.detailList?.observe(viewLifecycleOwner, Observer aa@{
            Log.d("PureBBS","<detail> DetailFragment viewModel.detailAdapter.submitList:$it")
            viewModel.detailAdapter.submitList(it)
        })
        return binding
    }
}
