package com.maxproj.purebbs.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maxproj.purebbs.R
import com.maxproj.purebbs.database.PostBrief

class PostAdapter : RecyclerView.Adapter<PostBriefItemViewHolder>() {
    var data =  listOf<PostBrief>()
        set(value) {
            Log.d("PureBBS", "adapter post data: $value")
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount():Int{
        Log.d("PureBBS", "adapter get data size: ${data.size}")
        return data.size
    }

    override fun onBindViewHolder(holder: PostBriefItemViewHolder, position: Int) {
        val item = data[position]
        Log.d("PureBBS", "adapter onBindViewHolder item: $item")
        holder.user.text = item.user
//        holder.title.text = item.postTitle
        Log.d("PureBBS", "adapter onBindViewHolder: $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostBriefItemViewHolder {
        Log.d("PureBBS", "adapter onCreateViewHolder")
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.post_item_view, parent, false)// as TextView
        return PostBriefItemViewHolder(view)
    }
}

class PostBriefItemViewHolder(view: View): RecyclerView.ViewHolder(view){
    var user:TextView
//    var title:TextView

    init {
        user = view.findViewById(R.id.user)
//        title = view.findViewById(R.id.title)
    }
}