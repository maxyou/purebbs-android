package com.maxproj.purebbs.config

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maxproj.purebbs.databinding.SettingsFragmentBinding


class SettingsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        val binding: SettingsFragmentBinding = bindingViewModelInit()

        return binding.root
    }

    private fun bindingViewModelInit(): SettingsFragmentBinding {

        val binding: SettingsFragmentBinding = SettingsFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        return binding
    }
}
