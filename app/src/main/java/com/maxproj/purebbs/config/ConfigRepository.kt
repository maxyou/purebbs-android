package com.maxproj.purebbs.config

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.net.HttpData
import com.maxproj.purebbs.net.HttpService
import com.maxproj.purebbs.post.Post
import com.maxproj.purebbs.post.PostBoundaryCallback
import com.maxproj.purebbs.post.PostDao
import com.maxproj.purebbs.post.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ConfigRepository(
    private val viewModelScope: CoroutineScope,
    private val configDao: ConfigDao,
    private val httpApi: HttpApi
) {

    var categoryList: LiveData<List<Config.Category>>? = configDao.getCategoryList()


    fun updateCategory(){
        Log.d("PureBBS", "<initCategory>")

        viewModelScope.launch {
            var data: HttpData.CategoryListRet
            try {
                Log.d("PureBBS", "<initCategory> before httpApi.getCategoryList")
                data = HttpService.api.getCategoryList()
                Log.d("PureBBS", "<initCategory> after httpApi.getCategoryList")
            }catch (he: HttpException){
                Log.d("PureBBS", "<initCategory> catch HttpException")
                Log.d("PureBBS", he.toString())
                return@launch
            }catch (throwable:Throwable){
                Log.d("PureBBS", "<initCategory> catch Throwable")
                Log.d("PureBBS", throwable.toString())
                return@launch
            }

            Log.d("PureBBS", "<initCategory> categories:${data.data.category}")
            configDao.insertList(data.data.category)

        }
    }
}