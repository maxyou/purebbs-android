package com.maxproj.purebbs.detail

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.maxproj.purebbs.config.LikeUser
import com.maxproj.purebbs.config.Oauth
import com.maxproj.purebbs.post.Post


@Entity(tableName = "detail_table")
data class Detail (
    @PrimaryKey var _id:String,
    val author:String = "unknow",
    val authorId:String = "-1",
    val postId:String,
    val avatarFileName:String?,
    val anonymous:Boolean = false,
    val source:String? = "register",
    val oauth: Oauth?,
//    val title:String? = "no title",
    val content:String? = "no content",
//    val category: String,
    val updated: String?,
    val created:String?,
//    val commentNum:Int? = 0,
//    val likeUser:List<LikeUser?>?,
//    val lastReplyId: String?,
//    val lastReplyName: String?,
//    val lastReplyTime: String?,
    val updatedById: String?,
    val updatedByName: String?
//    val allUpdated: String?,
//    val stickTop:Boolean? = false
//    val extend: Extend
)
