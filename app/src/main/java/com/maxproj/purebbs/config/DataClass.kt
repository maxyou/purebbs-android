package com.maxproj.purebbs.config

import android.util.Log
import com.google.gson.Gson

data class Oauth(val avatarUrl:String)
data class LikeUser(val _id: String, val name:String){
    companion object{
        fun fromJsonStr(str:String):LikeUser?{
            Log.d("PureBBS", "PostBrief.LikeUser.fromJsonStr: $str")
            return Gson().fromJson(str, LikeUser::class.java)
        }
    }
    fun toJsonStr():String?{
        val js = Gson().toJson(this)
        Log.d("PureBBS", "PostBrief.LikeUser.toJsonStr: $js")
        return js
    }
}
data class Extend(val addChoice:String){

}