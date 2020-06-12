package com.maxproj.purebbs.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.maxproj.purebbs.detail.Detail

class DetailBoundaryCallback(
//    private val viewModelScope: CoroutineScope,
//    private val httpApi: HttpApi,
//    private val detailDao: DetailDao,
    val boundaryGetMore:()->Unit
) : PagedList.BoundaryCallback<Detail>() {

    companion object{
        private const val NETWORK_PAGE_SIZE = 20
    }
    init {
        Log.d("PureBBS","<detail> DetailBoundaryCallback init{}")
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
        Log.d("RepoBoundaryCallback", "<detail> onZeroItemsLoaded")
        boundaryGetMore()
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: Detail) {
        Log.d("RepoBoundaryCallback", "<detail> onItemAtEndLoaded: $itemAtEnd")
        boundaryGetMore()
    }

}