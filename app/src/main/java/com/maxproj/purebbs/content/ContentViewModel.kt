package com.maxproj.purebbs.content

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maxproj.purebbs.net.HttpApi

class ContentViewModel(application: Application, httpApi: HttpApi) : AndroidViewModel(application){

}

class ContentViewModelFactory (private val application: Application, private val httpApi: HttpApi) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContentViewModel::class.java)) {
            return ContentViewModel(application, httpApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}