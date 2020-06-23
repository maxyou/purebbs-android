package com.maxproj.purebbs.post

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.google.gson.Gson
import com.maxproj.purebbs.config.Config
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.net.HttpData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PostRepository(
    private val viewModelScope: CoroutineScope,
    private val postDao: PostDao,
    private val httpApi: HttpApi
) {
    init {
        Log.d("PureBBS", "<lifecycle> PostRepository init")
        viewModelScope.launch(Dispatchers.IO){
            postDao.deleteAllPost()
        }
    }
    companion object {
        private const val DATABASE_PAGE_SIZE = 10
    }

    val postList: LiveData<PagedList<Post>>?
        get() {
            val dataSourceFactory = postDao.getPostDataSource()

//            val boundaryCallback = PostBoundaryCallback(viewModelScope, httpApi, postDao)
            val boundaryCallback = PostBoundaryCallback(::postBoundaryGetMore)

            return LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        }

    private suspend fun httpGetMore(): HttpData.PostListRet?{

        var data: HttpData.PostListRet? = null

        val postCount = postDao.getPostCount()
        Log.d("PureBBS", "<PostBoundaryCallback> getPostCount(): $postCount")
        val query = HttpData.PostListQuery(
            query = if (Config.categoryCurrentLive.value == Config.CATEGORY_ALL || Config.categoryCurrentLive.value == null)
                        null //category of all
                    else HttpData.PostListQuery.Category(
                        category = Config.categoryCurrentLive.value!!
                        ),
            options = HttpData.PostListQuery.Options(
                offset = postCount,
                limit = 10,
                sort = HttpData.PostListQuery.Options.Sort(allUpdated = -1),
                select = "source oauth title postId author authorId commentNum likeUser updated created avatarFileName lastReplyId lastReplyName lastReplyTime allUpdated stickTop category anonymous extend"
            )
        )
        val queryStr = Gson().toJson(query)
        Log.d("PureBBS", "<PostBoundaryCallback> queryStr: $queryStr")
        try {
            Log.d("PureBBS", "<PostBoundaryCallback> before httpApi.getPostByPaginate")
            data = httpApi.getPostByPaginate(queryStr)
            Log.d("PureBBS", "<PostBoundaryCallback> after httpApi.getPostByPaginate")
        } catch (he: HttpException) {
            Log.d("PureBBS", "<PostBoundaryCallback> catch HttpException")
            Log.d("PureBBS", he.toString())

        } catch (throwable: Throwable) {
            Log.d("PureBBS", "<PostBoundaryCallback> catch Throwable")
            Log.d("PureBBS", throwable.toString())

        }
        return data
    }

    private fun postBoundaryGetMore(){
        viewModelScope.launch(Dispatchers.IO) {
            val data = httpGetMore()
            if(data != null){
                postDao.insertList(data.data)
            }
        }
    }

    fun changeCategory(category:String){
        viewModelScope.launch(Dispatchers.IO) {
            if(postDao.getPostCount() == 0){
                val data = httpGetMore()
                if(data != null){
                    postDao.insertList(data.data)
                }
            }else{
                postDao.deleteAllPost()
            }
        }
    }

}