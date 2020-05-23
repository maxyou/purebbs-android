package com.maxproj.purebbs.home

import android.util.Log
import androidx.lifecycle.*
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.net.HttpData
import com.maxproj.purebbs.net.HttpService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository(
    private val viewModelScope: CoroutineScope,
    private val homeDao: HomeDao,
    private val httpApi: HttpApi
) {

    val serverInfo: LiveData<List<ServerInfo>> = homeDao.getServerInfo()
    var num: Int = 1
    fun serverInfoUpdate() {
        Log.d("PureBBS", "serverInfoUpdate()")
//        viewModelScope.launch(Dispatchers.IO) {
//            homeDao.deleteAllServerInfo()// Delete all content here.
//            // Add sample words.
//            var serverInfo = ServerInfo(
//                num,
//                "Alex $num"
//            )
//            homeDao.insertServerInfo(serverInfo)
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

                updateByHomeDao(data)

            }

            // Error case is left out for brevity.
            override fun onFailure(call: Call<HttpData.User>, t: Throwable) {
//                TODO()
            }
        })
        return data
    }

    private fun updateByHomeDao(data: MutableLiveData<HttpData.User>) {
        viewModelScope.launch(Dispatchers.IO) {

            Log.d("PureBBS", "viewModelScope call homeDao ${data.value}")

            homeDao.deleteAllServerInfo()// Delete all content here.
            // Add sample words.
            var serverInfo = ServerInfo(
                num,
                data.value?.name.toString()
            )
            homeDao.insertServerInfo(serverInfo)
            num++
        }
    }


    fun getJsonUserById(id: String){

        viewModelScope.launch(Dispatchers.IO) {

            val data = httpApi.getJsonUserById(id)

            Log.d("PureBBS", "getJsonUserById -------- viewModelScope call homeDao ${data}")

            homeDao.deleteAllServerInfo()// Delete all content here.
            // Add sample words.
            var serverInfo = ServerInfo(
                num,
                data?.name.toString()
            )
            homeDao.insertServerInfo(serverInfo)
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