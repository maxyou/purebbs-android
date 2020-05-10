package com.maxproj.purebbs.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.maxproj.purebbs.databinding.HomeFragmentBinding

class HomeFragment : Fragment(){
    private val viewModel by lazy {
        ViewModelProvider(activity as AppCompatActivity, ViewModelProvider.NewInstanceFactory()).get(ViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val binding: HomeFragmentBinding = HomeFragmentBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel

        return binding.root
//        return inflater.inflate(R.layout.home_fragment, container, false)
    }


}