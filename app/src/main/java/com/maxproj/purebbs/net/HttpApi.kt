package com.maxproj.purebbs.net

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*


/**
 * REST API access points.
 */
interface HttpApi {
//        @DELETE("rooms/{id}")
//        fun delLiveRoom(@Path("id") id: Int): Call<JsonCodeAndMessage?>?
//
//        @PUT("periods/{id}")
//        fun modfiyLiveSubjectRoom(
//            @Path("id") id: Int,
//            @Body jsonLiveModfiySubjectRoom: JsonLiveModfiySubjectRoom?
//        ): Call<JsonCodeAndMessage?>?
//
//
//        @POST("roles/student/{userId}/email")
//        fun addCrmStudentEmail(
//            @Path("userId") userId: Int,
//            @Body jsonCrmUpdataStudent: JsonCrmUpdataStudent?
//        ): Call<JsonCodeAndMessage?>?

//        @GET("post/getpage")
//        fun getCrmStudentGetAdjustCoursesList(): Call<JsonCrmStudentGetAdjustCourseRet?>?

//    @GET("post/getpage/{id}/messages")
//    fun getPostByPaginate(
//        @Path("id") id: Int,
//        @Query("cursor") createdAt: String?,
//        @Query("limit") limit: Int
//    ): Call<HttpData.JsonNewsGroupRet?>?

    @GET("post/getpage")
    fun getPostByPaginate(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Call<HttpData.PostListRet>?

    @GET("api/v1/topics")
    fun getHotTopics(
        @Query("page") page: Int,
        @Query("tab") tab: String,
        @Query("limit") limit: Int,
        @Query("mdrender") mdrender: Boolean
    ): Call<HttpData.CnNodeTopics>
}