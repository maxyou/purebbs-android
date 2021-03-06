package com.maxproj.purebbs.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.google.gson.Gson
import com.maxproj.purebbs.config.Config
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.net.HttpData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PostBoundaryCallback(
//    private val viewModelScope: CoroutineScope,
//    private val httpApi: HttpApi,
//    private val postDao: PostDao,
    val boundaryGetMore:()->Unit
) : PagedList.BoundaryCallback<Post>() {

    companion object{
        private const val NETWORK_PAGE_SIZE = 20
    }

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        Log.d("RepoBoundaryCallback", "<PostBoundaryCallback> onZeroItemsLoaded")
        boundaryGetMore()
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: Post) {
        Log.d("RepoBoundaryCallback", "<PostBoundaryCallback> onItemAtEndLoaded: $itemAtEnd")
        boundaryGetMore()
    }

}