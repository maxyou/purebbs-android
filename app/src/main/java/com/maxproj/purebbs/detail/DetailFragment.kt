package com.maxproj.purebbs.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.maxproj.purebbs.databinding.DetailFragmentBinding


class DetailFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(activity as AppCompatActivity, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DetailFragmentBinding = DetailFragmentBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel

        return binding.root
    }

}
