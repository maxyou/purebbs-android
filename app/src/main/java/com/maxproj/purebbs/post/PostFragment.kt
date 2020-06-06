package com.maxproj.purebbs.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

        viewModel.postList.observe(viewLifecycleOwner, Observer {
            Log.d("PureBBS", "Observed postList onChange")
            viewModel.postAdapter.data = it
        })
        return binding
    }

}

//fun mockPostData():List<PostBrief>{
//    var data =  mutableListOf<PostBrief>()
//
//    (0..20).forEach {
//        data.add(it,
//            PostBrief(
//                it.toString(),
//                "user: $it",
//                "",
//                "title $it - ${(100..200).random()}",
//                "category",
//                "2020-0519-0510-01-001",
//                1,
//                false
//            )
//        )
//        Log.d("PureBBS", "mock post data: $it")
//    }
//
//    println(data)
//    return data
//}

