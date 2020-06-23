package com.maxproj.purebbs.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.maxproj.purebbs.config.Config
import com.maxproj.purebbs.config.Config.categoryCurrentLive
import com.maxproj.purebbs.config.Config.updateAndCompareCategoryLiveStr
import com.maxproj.purebbs.config.SharedViewModel
import com.maxproj.purebbs.databinding.PostFragmentBinding
import com.maxproj.purebbs.net.HttpService

class PostFragment : Fragment(){

    private val viewModel by lazy {
        ViewModelProvider(this, PostViewModelFactory(this.requireActivity().application, HttpService.api))
            .get(PostViewModel::class.java)
    }

    private val sharedViewModel:SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("PureBBS", "<lifecycle> PostFragment onCreateView")

        setHasOptionsMenu(true)

        val binding: PostFragmentBinding = bindingViewModelInit()

        return binding.root
    }

    private fun bindingViewModelInit(): PostFragmentBinding {

        val binding: PostFragmentBinding = PostFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        //        viewModel.postAdapter.data = mockPostData()
        viewModel.postAdapter.viewModel = viewModel
        viewModel.postAdapter.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.postRecyclerview.adapter = viewModel.postAdapter

        viewModel.postList?.observe(viewLifecycleOwner, Observer {
            Log.d("PureBBS", "<PostBoundaryCallback> Observed PagedList onChange: $it")
            viewModel.postAdapter.submitList(it)
        })
        Log.d("PureBBS", "<category refresh> category live set observer")
        categoryCurrentLive.observe(viewLifecycleOwner, Observer {
            Log.d("PureBBS", "<category refresh> category observed change:${it}")
            if(updateAndCompareCategoryLiveStr(it)){
                Log.d("PureBBS", "<category refresh> category really changed, call process")
                viewModel.changeCategory(it)
            }
        })

        viewModel.sharedViewModel = sharedViewModel

        return binding
    }

}


