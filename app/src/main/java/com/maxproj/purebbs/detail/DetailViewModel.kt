package com.maxproj.purebbs.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.maxproj.purebbs.config.MyRoomDatabase
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.detail.Detail
import com.maxproj.purebbs.detail.DetailAdapter
import com.maxproj.purebbs.detail.DetailRepository
import com.maxproj.purebbs.detail.DetailViewModel

class DetailViewModel(application: Application, httpApi: HttpApi, postId: String) : ViewModel() {

    var postId:String? = null
    private var detailRepository: DetailRepository
    val detailList: LiveData<PagedList<Detail>>?

    init {
        Log.d("PureBBS","<detail> DetailViewModel init{}")
        val detailDao = MyRoomDatabase.getDatabase(application, viewModelScope).detailDao()
        detailRepository = DetailRepository(viewModelScope, detailDao, httpApi, postId)
        detailList = detailRepository.detailList
    }

    var detailAdapter: DetailAdapter = DetailAdapter()

//    fun changePostId(postId:String){
//        detailRepository.changePostId(postId)
//    }
}

class DetailViewModelFactory (private val application: Application, private val httpApi: HttpApi, private val postId: String) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(application, httpApi, postId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
