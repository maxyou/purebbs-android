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

    fun refreshPostList(queryStr:String) {

        var data:HttpData.PostListRet

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("PureBBS", "before httpApi.getPostByPaginate")
                data = httpApi.getPostByPaginate(queryStr)
                Log.d("PureBBS", "after httpApi.getPostByPaginate")
            }catch (he:HttpException){
                Log.d("PureBBS", "catch HttpException")
                Log.d("PureBBS", he.toString())
                return@launch
            }catch (throwable:Throwable){
                Log.d("PureBBS", "catch Throwable")
                Log.d("PureBBS", throwable.toString())
                return@launch
            }

            postDao.deleteAllPost()
            Log.d("PureBBS", "http get: ${data.data}")
            postDao.insertList(data.data)
            Log.d("PureBBS", "after postDao insert list")

        }
    }

}