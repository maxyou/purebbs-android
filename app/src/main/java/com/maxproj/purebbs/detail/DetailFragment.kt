package com.maxproj.purebbs.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.maxproj.purebbs.config.CategoryAdapter
import com.maxproj.purebbs.config.Config
import com.maxproj.purebbs.databinding.DetailFragmentBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlin.text.category


class DetailFragment : Fragment() {
    val adapter = CategoryAdapter()
    private val viewModel by lazy {
        ViewModelProvider(activity as AppCompatActivity, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DetailFragmentBinding = DetailFragmentBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel

        adapter.lifecycleOwner = this
        val mock = (0..150).map { Config.Category("aaa","bbb $it") }
        adapter.submitList(mock)
        binding.detailRecyclerView.adapter = adapter

        return binding.root
    }

}
