package com.maxproj.purebbs.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.maxproj.purebbs.R
import com.maxproj.purebbs.config.Config
import com.maxproj.purebbs.databinding.DetailFragmentBinding
import com.maxproj.purebbs.net.HttpService
import com.maxproj.purebbs.detail.DetailViewModel
import com.maxproj.purebbs.detail.DetailViewModelFactory
import kotlinx.android.synthetic.main.detail_fragment.*


class DetailFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this.requireActivity(), DetailViewModelFactory(this.requireActivity().application, HttpService.api))
            .get(DetailViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val safeArgs: DetailFragmentArgs by navArgs()
//        val detailId = safeArgs.detailId
//
//        val v: View = inflater.inflate(R.layout.detail_fragment, container, false)
//        val tv = v.findViewById<TextView>(R.id.textView)
//        tv.setText("detailId: $detailId")
//
//        return v
        val binding: DetailFragmentBinding = bindingViewModelInit()

        return binding.root
    }


    private fun bindingViewModelInit(): DetailFragmentBinding {

        val binding: DetailFragmentBinding = DetailFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        //        viewModel.detailAdapter.data = mockDetailData()
        viewModel.detailAdapter.viewModel = viewModel
        viewModel.detailAdapter.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.detailRecyclerview.adapter = viewModel.detailAdapter

        viewModel.detailList?.observe(viewLifecycleOwner, Observer {
            Log.d("PureBBS", "<DetailBoundaryCallback> Observed PagedList onChange: $it")
            viewModel.detailAdapter.submitList(it)
        })
//        Config.categoryCurrentLive.observe(viewLifecycleOwner, Observer {
//            viewModel.changeCategory(it)
//        })
        return binding
    }
}
