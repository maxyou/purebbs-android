package com.maxproj.purebbs.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.maxproj.purebbs.database.PostBrief
import com.maxproj.purebbs.databinding.HomeFragmentBinding

class HomeFragment : Fragment(){
    private val viewModel by lazy {
        ViewModelProvider(activity as AppCompatActivity, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val binding: HomeFragmentBinding = HomeFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.postAdapter.data = mockPostData()
        viewModel.postAdapter.viewModel = viewModel
        viewModel.postAdapter.lifecycleOwner = this
        binding.homeRecyclerview.adapter = viewModel.postAdapter

        return binding.root
//        return inflater.inflate(R.layout.home_fragment, container, false)
    }

}

fun mockPostData():List<PostBrief>{
    var data =  mutableListOf<PostBrief>()

    (0..20).forEach {
        data.add(it, PostBrief(it, "user: $it", "title $it", "category"))
        Log.d("PureBBS", "mock post data: $it")
    }

    println(data)
    return data
}