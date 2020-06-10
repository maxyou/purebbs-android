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
    private val viewModelScope: CoroutineScope,
    private val httpApi: HttpApi,
    private val postDao: PostDao
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
        requestAndSaveData()
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: Post) {
        Log.d("RepoBoundaryCallback", "<PostBoundaryCallback> onItemAtEndLoaded: $itemAtEnd")
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
//        if (isRequestInProgress) return
//        isRequestInProgress = true

        viewModelScope.launch(Dispatchers.IO) {
            val postCount = postDao.getPostCount()
            Log.d("PureBBS", "<PostBoundaryCallback> getPostCount(): $postCount")
            val query = HttpData.PostListQuery(
                query = HttpData.PostListQuery.Category(category = Config.categoryCurrent),
                options = HttpData.PostListQuery.Options(
                    offset = postCount,
                    limit = 10,
                    sort = HttpData.PostListQuery.Options.Sort(allUpdated = -1),
                    select = "source oauth title postId author authorId commentNum likeUser updated created avatarFileName lastReplyId lastReplyName lastReplyTime allUpdated stickTop category anonymous extend"
                )
            )
            val queryStr = Gson().toJson(query)
            Log.d("PureBBS", "<PostBoundaryCallback> queryStr: $queryStr")
            var data:HttpData.PostListRet
            try {
                Log.d("PureBBS", "<PostBoundaryCallback> before httpApi.getPostByPaginate")
                data = httpApi.getPostByPaginate(queryStr)
                Log.d("PureBBS", "<PostBoundaryCallback> after httpApi.getPostByPaginate")
            }catch (he: HttpException){
                Log.d("PureBBS", "<PostBoundaryCallback> catch HttpException")
                Log.d("PureBBS", he.toString())
                return@launch
            }catch (throwable:Throwable){
                Log.d("PureBBS", "<PostBoundaryCallback> catch Throwable")
                Log.d("PureBBS", throwable.toString())
                return@launch
            }

//            postDao.deleteAllPost()
            Log.d("PureBBS", "<PostBoundaryCallback> http get: ${data.data}")
            postDao.insertList(data.data)
            Log.d("PureBBS", "<PostBoundaryCallback> after postDao insert list")
        }
    }
}