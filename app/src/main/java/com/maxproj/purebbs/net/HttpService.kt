package com.maxproj.purebbs.net

import com.maxproj.purebbs.Config
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory

import java.io.IOException

object HttpService {



    val api by lazy {
        val interceptor: Interceptor = object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                request = request.newBuilder()
//                    .addHeader("Authorization", "Bearer " + "usr_token")
                    .addHeader("Connection","close")
                    .addHeader("content-type", "application/json")
                    .build()
                return chain.proceed(request)
            }
        }
        val okHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
//            .sslSocketFactory(co.docy.ourgroups.NetTools.HttpTools.sslContext.getSocketFactory())
            .addNetworkInterceptor(interceptor).build()

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Config.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build().create(HttpApi::class.java)
    }

    private fun getRetrofit(): Retrofit {
        val interceptor: Interceptor = object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                request = request.newBuilder()
//                    .addHeader("Authorization", "Bearer " + "usr_token")
                    .addHeader("Connection","close")
                    .addHeader("content-type", "application/json")
                    .build()
                return chain.proceed(request)
            }
        }
        val okHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
//            .sslSocketFactory(co.docy.ourgroups.NetTools.HttpTools.sslContext.getSocketFactory())
            .addNetworkInterceptor(interceptor).build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Config.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
    }
    fun api(): HttpApi {
        return getRetrofit().create(HttpApi::class.java)
    }
}