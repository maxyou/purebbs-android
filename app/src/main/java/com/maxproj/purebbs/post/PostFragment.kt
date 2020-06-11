package com.maxproj.purebbs.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.maxproj.purebbs.config.Config.categoryCurrentLive
import com.maxproj.purebbs.databinding.PostFragmentBinding
import com.maxproj.purebbs.net.HttpService

class PostFragment : Fragment(){

    private val viewModel by lazy {
//        var activity = activity as AppCompatActivity
        ViewModelProvider(this.requireActivity(), PostViewModelFactory(this.requireActivity().application, HttpService.api))
            .get(PostViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        categoryCurrentLive.observe(viewLifecycleOwner, Observer {
            viewModel.changeCategory(it)
        })
        return binding
    }

}


