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
    suspend fun getPostByPaginate(
        @Query("pageInfo") pageInfo: String
    ): HttpData.PostListRet

    @GET("api/v1/topics")
    fun getHotTopics(
        @Query("page") page: Int,
        @Query("tab") tab: String,
        @Query("limit") limit: Int,
        @Query("mdrender") mdrender: Boolean
    ): Call<HttpData.CnNodeTopics>

    @GET("json")
    fun getExpressJson(): Call<HttpData.ExpressJson>?

    @GET("userId")
    fun getUserById(
        @Query("userId") tab: String
    ): Call<HttpData.User>

    @GET("jsonUserId")
    suspend fun getJsonUserById(
        @Query("jsonUserId") tab: String
    ): HttpData.User

    @GET("jsonUserId")
    suspend fun getJsonUserByIdR(
        @Query("jsonUserId") tab: String
    ): Response<HttpData.User>
}