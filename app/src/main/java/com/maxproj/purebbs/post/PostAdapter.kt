package com.maxproj.purebbs.post

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.maxproj.purebbs.databinding.PostItemViewBinding

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

