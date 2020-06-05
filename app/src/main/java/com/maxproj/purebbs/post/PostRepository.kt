package com.maxproj.purebbs.post

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.net.HttpData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class PostRepository(
    private val viewModelScope: CoroutineScope,
    private val postDao: PostDao,
    private val httpApi: HttpApi
) {

    var postList: LiveData<List<PostBrief>> = postDao.getPostList()

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
            postDao.insertList(data.data.map {
                Log.d("PureBBS", it.toString())
                PostBrief(
                    _id = it._id,
                    user = it.authorId,
                    avatarFileName = it.avatarFileName,
                    postTitle = it.title,
                    postCategory = it.category,
                    createTime = it.created.toString(),
                    commentNum = it.commentNum,
                    stickTop = it.stickTop)
            })
        }
    }

//    fun tryHttp(){
//        var repos = HttpService.api().getExpressJson()
//
//        Log.d("PureBBS", repos.toString())
//
//        repos?.enqueue(object: Callback<HttpData.ExpressJson> {
//            override fun onResponse(call: Call<HttpData.ExpressJson>, response: Response<HttpData.ExpressJson>){
//                Log.d("PureBBS", "onResponse=======================")
//                Log.d("PureBBS", response.body().toString())
//                Log.d("PureBBS", response.toString())
//                if(response.isSuccessful){
//
//                }
//            }
//
//            override fun onFailure(call: Call<HttpData.ExpressJson>, t: Throwable) {
//                Log.d("PureBBS", "t.localizedMessage=======================")
//                Log.d("PureBBS", t.toString())
//                Log.d("PureBBS", t.localizedMessage.toString())
//            }
//        })
//
//    }
}