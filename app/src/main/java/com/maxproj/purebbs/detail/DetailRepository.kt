package com.maxproj.purebbs.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.google.gson.Gson
import com.maxproj.purebbs.config.Config
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.net.HttpData
import com.maxproj.purebbs.detail.Detail
import com.maxproj.purebbs.detail.DetailBoundaryCallback
import com.maxproj.purebbs.detail.DetailDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailRepository (
    private val viewModelScope: CoroutineScope,
    private val detailDao: DetailDao,
    private val httpApi: HttpApi
) {
    companion object {
        private const val DATABASE_PAGE_SIZE = 10
    }

    val detailList: LiveData<PagedList<Detail>>?
        get() {
            val dataSourceFactory = detailDao.getDetailDataSource()

//            val boundaryCallback = DetailBoundaryCallback(viewModelScope, httpApi, detailDao)
            val boundaryCallback = DetailBoundaryCallback(::detailBoundaryGetMore)

            return LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        }

    private suspend fun httpGetMore(): HttpData.DetailListRet?{

        var data: HttpData.DetailListRet? = null

        val detailCount = detailDao.getDetailCount()
        Log.d("PureBBS", "<DetailBoundaryCallback> getDetailCount(): $detailCount")
        val query = HttpData.DetailListQuery(
            query = if (Config.categoryCurrentLive.value == Config.CATEGORY_ALL || Config.categoryCurrentLive.value == null)
                null //category of all
            else HttpData.DetailListQuery.Category(
                category = Config.categoryCurrentLive.value!!
            ),
            options = HttpData.DetailListQuery.Options(
                offset = detailCount,
                limit = 10,
                sort = HttpData.DetailListQuery.Options.Sort(allUpdated = -1),
                select = "source oauth title detailId author authorId commentNum likeUser updated created avatarFileName lastReplyId lastReplyName lastReplyTime allUpdated stickTop category anonymous extend"
            )
        )
        val queryStr = Gson().toJson(query)
        Log.d("PureBBS", "<DetailBoundaryCallback> queryStr: $queryStr")
        try {
            Log.d("PureBBS", "<DetailBoundaryCallback> before httpApi.getDetailByPaginate")
            data = httpApi.getDetailByPaginate(queryStr)
            Log.d("PureBBS", "<DetailBoundaryCallback> after httpApi.getDetailByPaginate")
        } catch (he: HttpException) {
            Log.d("PureBBS", "<DetailBoundaryCallback> catch HttpException")
            Log.d("PureBBS", he.toString())

        } catch (throwable: Throwable) {
            Log.d("PureBBS", "<DetailBoundaryCallback> catch Throwable")
            Log.d("PureBBS", throwable.toString())

        }
        return data
    }

    private fun detailBoundaryGetMore(){
        viewModelScope.launch(Dispatchers.IO) {
            val data = httpGetMore()
            if(data != null){
                detailDao.insertList(data.data)
            }
        }
    }

    fun changeCategory(category:String){
        viewModelScope.launch(Dispatchers.IO) {
            if(detailDao.getDetailCount() == 0){
                val data = httpGetMore()
                if(data != null){
                    detailDao.insertList(data.data)
                }
            }else{
                detailDao.deleteAllDetail()
            }
        }
    }

}