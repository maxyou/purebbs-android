package com.maxproj.purebbs.home

import android.util.Log
import androidx.lifecycle.*
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.net.HttpData
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
        viewModelScope.launch(Dispatchers.IO) {
            homeDao.deleteAllServerInfo()// Delete all content here.
            // Add sample words.
            var serverInfo = ServerInfo(
                num,
                "Alex $num"
            )
            homeDao.insertServerInfo(serverInfo)
            num++
        }

    }

    // ...
    fun getUser(userId: String): LiveData<HttpData.User> {
        // This isn't an optimal implementation. We'll fix it later.
        val data = MutableLiveData<HttpData.User>()
        httpApi.getUserById(userId).enqueue(object : Callback<HttpData.User> {
            override fun onResponse(call: Call<HttpData.User>, response: Response<HttpData.User>) {
                data.value = response.body()
            }

            // Error case is left out for brevity.
            override fun onFailure(call: Call<HttpData.User>, t: Throwable) {
                TODO()
            }
        })
        return data
    }


}