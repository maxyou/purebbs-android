package com.maxproj.purebbs.post

import android.util.Log
import androidx.lifecycle.*
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

    var postList: LiveData<List<Post>> = postDao.getPostList()

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
//            postDao.insertPost(
//                PostBrief(
//                    _id = "it.toString()",
//                    author = "user",
//                    authorId = "authorId",
//                    postId = "postId",
//                    category = "category",
//                    created = "2020-0519-0510-01-001",
//                    commentNum = 1,
//                    avatarFileName = "aaa",
//                    source = "register",
//                    title = "title",
//                    content = "content",
//                    allUpdated = "2020-0519-0510-01-001",
//                    updated = "2020-0519-0510-01-001",
//                    stickTop = false
//                )
//            )
//            Log.d("PureBBS", "after postDao insert PostBrief")
        }
    }

    //            PostBrief(
//                it.toString(),
//                "user: $it",
//                "",
//                "title $it - ${(100..200).random()}",
//                "category",
//                "2020-0519-0510-01-001",
//                1,
//                false
//            )
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