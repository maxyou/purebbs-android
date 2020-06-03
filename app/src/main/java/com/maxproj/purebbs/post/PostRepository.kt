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
import retrofit2.Response

class PostRepository(
    private val viewModelScope: CoroutineScope,
    private val postDao: PostDao,
    private val httpApi: HttpApi
) {

    val serverInfo: LiveData<List<ServerInfo>> = postDao.getServerInfo()
    var num: Int = 1
    fun serverInfoUpdate() {
        Log.d("PureBBS", "serverInfoUpdate()")
//        viewModelScope.launch(Dispatchers.IO) {
//            postDao.deleteAllServerInfo()// Delete all content here.
//            // Add sample words.
//            var serverInfo = ServerInfo(
//                num,
//                "Alex $num"
//            )
//            postDao.insertServerInfo(serverInfo)
//            num++
//        }

//        getUser(num.toString())
//        tryHttp()
    }

    fun tryHttp(){

//        var repos = HttpService.api().getPostByPaginate(0, 20)
//        var repos = HttpService.api().getPostByPaginate(0,20 )
//        var repos = HttpService.api().getV2exHot()
        var repos = httpApi.getExpressJson()
//        var repos = HttpService.api().getHotTopics(0, "ask", 10, false)

        Log.d("PureBBS", repos.toString())

        repos?.enqueue(object: Callback<HttpData.ExpressJson> {
            override fun onResponse(call: Call<HttpData.ExpressJson>, response: Response<HttpData.ExpressJson>){
                Log.d("PureBBS", "onResponse=======================@@@")
                Log.d("PureBBS", response.body().toString())
                Log.d("PureBBS", response.toString())
                if(response.isSuccessful){

                }
            }

            override fun onFailure(call: Call<HttpData.ExpressJson>, t: Throwable) {
                Log.d("PureBBS", "t.localizedMessage=======================")
                Log.d("PureBBS", t.toString())
                Log.d("PureBBS", t.localizedMessage.toString())
            }
        })

    }

    private fun getUser(userId: String): LiveData<HttpData.User> {

        // This isn't an optimal implementation. We'll fix it later.
        val data = MutableLiveData<HttpData.User>()

        Log.d("PureBBS", "getUser $userId")
        Log.d("PureBBS", "httpApi $httpApi")
        var repo = httpApi.getUserById(userId)

        Log.d("PureBBS", "repo ${repo.toString()}")

        repo.enqueue(object : Callback<HttpData.User> {
            override fun onResponse(call: Call<HttpData.User>, response: Response<HttpData.User>) {
                data.value = response.body()
                Log.d("PureBBS", "getUser return ${data.value}")

                updateByPostDao(data)

            }

            // Error case is left out for brevity.
            override fun onFailure(call: Call<HttpData.User>, t: Throwable) {
//                TODO()
            }
        })
        return data
    }

    private fun updateByPostDao(data: MutableLiveData<HttpData.User>) {
        viewModelScope.launch(Dispatchers.IO) {

            Log.d("PureBBS", "viewModelScope call postDao ${data.value}")

            postDao.deleteAllServerInfo()// Delete all content here.
            // Add sample words.
            var serverInfo = ServerInfo(
                num,
                data.value?.name.toString()
            )
            postDao.insertServerInfo(serverInfo)
            num++
        }
    }


    fun getJsonUserById(id: String){

        viewModelScope.launch(Dispatchers.IO) {

            val data = httpApi.getJsonUserById(id)

            Log.d("PureBBS", "getJsonUserById -------- viewModelScope call postDao ${data}")

            postDao.deleteAllServerInfo()// Delete all content here.
            // Add sample words.
            var serverInfo = ServerInfo(
                num,
                data?.name.toString()
            )
            postDao.insertServerInfo(serverInfo)
            num++
        }


    }


    fun getPostList(){
        viewModelScope.launch(Dispatchers.IO) {

//            val pageInfo:String = "{\"query\":{},\"options\":{\"offset\":0,\"limit\":10,\"sort\":{\"allUpdated\":-1},\"select\":\"source oauth title postId author authorId commentNum likeUser updated created avatarFileName lastReplyId lastReplyName lastReplyTime allUpdated stickTop category anonymous extend\"}}"
//            val data = httpApi.getPostByPaginate(pageInfo)

            val query = HttpData.PostListQuery(
                query = HttpData.PostListQuery.Category("category_dev_web"),
                options = HttpData.PostListQuery.Options(
                    offset = 0,
                    limit = 10,
                    sort = HttpData.PostListQuery.Options.Sort(allUpdated = -1),
                    select = "source oauth title postId author authorId commentNum likeUser updated created avatarFileName lastReplyId lastReplyName lastReplyTime allUpdated stickTop category anonymous extend"
                )
            )
            val queryStr = GsonBuilder().create().toJson(query)
            val queryStr2 = Gson().toJson(query)
            Log.d("PureBBS", "query string: $queryStr")
            Log.d("PureBBS", "query2 string: $queryStr2")
            val data = httpApi.getPostByPaginate(queryStr)
//            val data = httpApi.getPostByPaginate(object {
//                val query = HttpData.QueryPostList.QueryCategory("category_dev_web")
//                val options = HttpData.QueryPostList.Options(
//                    offset = 0,
//                    limit = 10,
//                    sort = HttpData.QueryPostList.Options.Sort(allUpdated = -1),
//                    select = "source oauth title postId author authorId commentNum likeUser updated created avatarFileName lastReplyId lastReplyName lastReplyTime allUpdated stickTop category anonymous extend"
//                )
//            }.toString())

            Log.d("PureBBS", "getPostList ${data.toString()}")

            postDao.deleteAllServerInfo()// Delete all content here.
            // Add sample words.
            var serverInfo = ServerInfo(
                num,
                data?.message
            )
            postDao.insertServerInfo(serverInfo)
            num++
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