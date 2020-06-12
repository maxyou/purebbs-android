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
    private val httpApi: HttpApi,
    private val postId: String
) {
    companion object {
        private const val DATABASE_PAGE_SIZE = 10
    }

    init {
        Log.d("PureBBS","<detail> DetailRepository init{}")
    }
    val detailList: LiveData<PagedList<Detail>>?
        get() {
            Log.d("PureBBS","<detail> DetailRepository detailList get()")

            val dataSourceFactory = detailDao.getDetailDataSource()

            val boundaryCallback = DetailBoundaryCallback(::detailBoundaryGetMore)

            return LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        }

    private suspend fun httpGetMore(): HttpData.DetailListRet?{

        var data: HttpData.DetailListRet? = null

        val detailCount = detailDao.getDetailCount()
        Log.d("PureBBS", "<detail> getDetailCount(): $detailCount")
        val query = HttpData.DetailListQuery(
            query = HttpData.DetailListQuery.PostID(postId = postId!!),
            options = HttpData.DetailListQuery.Options(
                offset = detailCount,
                limit = 10,
                sort = HttpData.DetailListQuery.Options.Sort(allUpdated = -1),
                select = "postId content author authorId updated created avatarFileName likeUser anonymous source oauth"
            )
        )
        val queryStr = Gson().toJson(query)
        Log.d("PureBBS", "<detail> queryStr: $queryStr")
        try {
            Log.d("PureBBS", "<detail> before httpApi.getDetailByPaginate")
            data = httpApi.getDetailByPaginate(queryStr)
            Log.d("PureBBS", "<detail> after httpApi.getDetailByPaginate")
        } catch (he: HttpException) {
            Log.d("PureBBS", "<detail> catch HttpException")
            Log.d("PureBBS", he.toString())

        } catch (throwable: Throwable) {
            Log.d("PureBBS", "<detail> catch Throwable")
            Log.d("PureBBS", throwable.toString())

        }
        Log.d("PureBBS", "<detail> get data: $data")
        return data
    }

    private fun detailBoundaryGetMore(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("PureBBS", "<detail> before get more")
            val data = httpGetMore()
            Log.d("PureBBS", "<detail> after get more")
            if(data != null){
                Log.d("PureBBS", "<detail> detailBoundaryGetMore.insertList():${data.data}")
                detailDao.insertList(data.data)
                Log.d("PureBBS", "<detail> detailBoundaryGetMore.insertList() end")
            }
        }
    }
//
//    fun changePostId(postId:String){
//        Log.d("PureBBS","<detail> DetailRepository changePostId()")
//        this.postId = postId
//        viewModelScope.launch(Dispatchers.IO) {
//            if(detailDao.getDetailCount() == 0){
//                val data = httpGetMore()
//                Log.d("PureBBS", "<detail> changePostId.insertList():${data?.data} --- before null check")
//                if(data != null){
//                    Log.d("PureBBS", "<detail> changePostId.insertList():${data.data}")
//                    detailDao.insertList(data.data)
//                    Log.d("PureBBS", "<detail> changePostId.insertList() end")
//                }
//            }else{
//                Log.d("PureBBS", "<detail> changePostId.deleteAllDetail() end")
//                detailDao.deleteAllDetail()
//                Log.d("PureBBS", "<detail> changePostId.deleteAllDetail() end")
//            }
//        }
//    }
}