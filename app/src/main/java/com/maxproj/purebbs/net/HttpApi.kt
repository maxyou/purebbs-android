package com.maxproj.purebbs.net

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*


/**
 * REST API access points.
 */
interface HttpApi {

    @GET("detail/getpage")
    suspend fun getDetailByPaginate(
        @Query("pageInfo") pageInfo: String
    ): HttpData.DetailListRet


    @GET("post/getpage")
    suspend fun getPostByPaginate(
        @Query("pageInfo") pageInfo: String
    ): HttpData.PostListRet

    //{ code: 0, message: '获取数据成功', data: {category: appConfig.category} };
    @GET("sys/category")
    suspend fun getCategoryList(): HttpData.CategoryListRet

}