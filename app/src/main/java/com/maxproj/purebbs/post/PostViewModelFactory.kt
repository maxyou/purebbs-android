package com.maxproj.purebbs.post

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maxproj.purebbs.net.HttpApi

class PostViewModelFactory (private val application: Application, private val httpApi: HttpApi) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostViewModel(application, httpApi) as T
    }
}