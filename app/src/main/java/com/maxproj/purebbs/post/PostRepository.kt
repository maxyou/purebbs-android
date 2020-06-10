package com.maxproj.purebbs.post

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.net.HttpData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PostRepository(
    private val viewModelScope: CoroutineScope,
    private val postDao: PostDao,
    private val httpApi: HttpApi
) {
    companion object {
        private const val DATABASE_PAGE_SIZE = 10
    }

    val postList: LiveData<PagedList<Post>>?
        get() {
            val dataSourceFactory = postDao.getPostDataSource()

            val boundaryCallback = PostBoundaryCallback(viewModelScope, httpApi, postDao)

            return LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        }

    fun clearPostList(){
        viewModelScope.launch {
            postDao.deleteAllPost()
        }
    }
}