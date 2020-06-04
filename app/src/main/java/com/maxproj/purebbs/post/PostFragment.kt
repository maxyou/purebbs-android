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
//        ViewModelProvider(activity as AppCompatActivity, ViewModelProvider.NewInstanceFactory()).get(PostViewModel::class.java)
        var activity = activity as AppCompatActivity
//        ViewModelProvider(activity, PostViewModelFactory(activity.application, HttpService.api())).get(PostViewModel::class.java)
        ViewModelProvider(activity, PostViewModelFactory(activity.application, HttpService.api)).get(PostViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val binding: PostFragmentBinding = PostFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
//        viewModel.postAdapter.data = mockPostData()
        viewModel.postAdapter.viewModel = viewModel
        viewModel.postAdapter.lifecycleOwner = this
        binding.postRecyclerview.adapter = viewModel.postAdapter

        viewModel.postList.observe(viewLifecycleOwner, Observer {
            viewModel.postAdapter.data = it
        })

        return binding.root
//        return inflater.inflate(R.layout.post_fragment, container, false)
    }

}

fun mockPostData():List<PostBrief>{
    var data =  mutableListOf<PostBrief>()

    (0..20).forEach {
        data.add(it,
            PostBrief(
                it.toString(),
                "user: $it",
                "title $it - ${(100..200).random()}",
                "category",
                "2020-0519-0510-01-001"
            )
        )
        Log.d("PureBBS", "mock post data: $it")
    }

    println(data)
    return data
}

