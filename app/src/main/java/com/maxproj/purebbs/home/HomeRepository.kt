package com.maxproj.purebbs.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.net.HttpData
import com.maxproj.purebbs.net.HttpService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository(private val homeDao: HomeDao) {

    private val httpApi: HttpApi = HttpService.api()
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