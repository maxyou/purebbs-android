package com.maxproj.purebbs.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maxproj.purebbs.net.HttpApi

class HomeViewModelFactory (private val application: Application, private val httpApi: HttpApi) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(application, httpApi) as T
    }
}