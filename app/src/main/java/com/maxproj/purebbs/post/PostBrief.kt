package com.maxproj.purebbs.post

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import java.io.Serializable
import java.util.*

@Entity(tableName = "post_table")
data class PostBrief (
    @PrimaryKey var _id:String,
    val author:String = "unknow",
    val authorId:String = "-1",
    val postId:String,
    val avatarFileName:String?,
    val anonymous:Boolean = false,
    val source:String = "register",
//    val oauth: Oauth?,
    val title:String? = "no title",
    val content:String? = "no content",
    val category: String,
    val updated: String,
    val created:String?,
    val postCategory:String?,
    val commentNum:Int? = 0,
//    val likeUser:List<LikeUser>,
    val lastReplyId: String,
    val lastReplyName: String,
    val lastReplyTime: String,
    val updatedById: String,
    val updatedByName: String,
    val allUpdated: String,
    val stickTop:Boolean? = false
//    val extend: Extend
){
    data class Oauth(val avatarUrl:String)
    data class LikeUser(val _id: String, val name:String){
        companion object{
            fun fromJsonStr(str:String):LikeUser{
                return Gson().fromJson(str, LikeUser::class.java)
            }
        }
        fun toJsonStr():String{
            return Gson().toJson(this)
        }
    }
    data class Extend(val addChoice:String){

    }
}
