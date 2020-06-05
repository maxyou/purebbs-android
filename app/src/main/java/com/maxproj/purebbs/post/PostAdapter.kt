package com.maxproj.purebbs.post

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maxproj.purebbs.Config
import com.maxproj.purebbs.databinding.PostItemViewBinding
import java.net.URL


class PostAdapter : RecyclerView.Adapter<PostAdapter.PostBriefItemViewHolder>() {

    lateinit var viewModel: PostViewModel
    lateinit var lifecycleOwner: LifecycleOwner

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
        holder.bind(item, viewModel)
//        holder.user.text = item.user
//        holder.title.text = item.postTitle
        Log.d("PureBBS", "adapter onBindViewHolder: $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostBriefItemViewHolder {
        val binding = PostItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = lifecycleOwner

        Log.d("PureBBS", "adapter onCreateViewHolder")
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val view = layoutInflater
//            .inflate(R.layout.post_item_view, parent, false)// as TextView
        return PostBriefItemViewHolder(binding)
    }

    class PostBriefItemViewHolder(val binding: PostItemViewBinding ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: PostBrief, viewModel:PostViewModel){
            binding.viewModel = viewModel
            binding.item = item
            binding.executePendingBindings() //
        }
    }
}

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, url: String?){
    Log.d("PureBBS", "image url:$url")
    if(url != null){
        val path:String = URL(Config.baseUrl, Config.avatarPath + url).toString()
        Log.d("PureBBS", "image path:$path")
        Glide.with(view.context).load("${path}").into(view)
    }
}

