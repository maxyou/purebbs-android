package com.maxproj.purebbs.net

import com.google.gson.annotations.SerializedName
import com.maxproj.purebbs.config.Config
import com.maxproj.purebbs.detail.Detail
import com.maxproj.purebbs.post.Post

class HttpData {

    data class DetailListQuery(
        val query:Category?,
        val options:Options
    ){
        data class Category(val category:String)

        data class Options(
            val offset:Int,
            val limit:Int,
            val sort:Sort,
            val select:String
        ){
            data class Sort(val allUpdated: Int)
        }
    }
    data class DetailListRet(val code:Int, val message:String, val data:List<Detail>, val totalDocs:Int)


    data class PostListQuery(
        val query:Category?,
        val options:Options
    ){
        data class Category(val category:String)

        data class Options(
            val offset:Int,
            val limit:Int,
            val sort:Sort,
            val select:String
        ){
            data class Sort(val allUpdated: Int)
        }
    }
    data class PostListRet(val code:Int, val message:String, val data:List<Post>, val totalDocs:Int)

    data class CategoryObject(val category: List<Config.Category>)
    data class CategoryListRet(val code:Int, val message:String, val data:CategoryObject, val totalDocs:Int)
}
