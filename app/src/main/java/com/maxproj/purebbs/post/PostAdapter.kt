package com.maxproj.purebbs.post

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maxproj.purebbs.config.Config
import com.maxproj.purebbs.databinding.PostItemViewBinding


class PostAdapter : PagedListAdapter<Post, PostAdapter.PostItemViewHolder>(POST_COMPARATOR) {

    lateinit var viewModel: PostViewModel
    lateinit var lifecycleOwner: LifecycleOwner

//    var data = listOf<Post>()
//        set(value) {
//            Log.d("PureBBS", "adapter post data: $value")
//            field = value
//            notifyDataSetChanged()
//        }
//
//    override fun getItemCount(): Int {
//        Log.d("PureBBS", "adapter get data size: ${data.size}")
//        return data.size
//    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        Log.d("PureBBS", "adapter onBindViewHolder")
        val item = getItem(position)
        Log.d("PureBBS", "adapter onBindViewHolder item: $item")
        holder.bind(item!!, viewModel)
//        holder.user.text = item.user
//        holder.title.text = item.postTitle
        Log.d("PureBBS", "adapter onBindViewHolder: $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {

        val binding =
            PostItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.lifecycleOwner = lifecycleOwner

        return PostItemViewHolder(binding)
    }

    class PostItemViewHolder(val binding: PostItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post, viewModel: PostViewModel) {
            binding.viewModel = viewModel
            binding.item = item
            binding.executePendingBindings() //
        }
    }

    companion object {
        private val POST_COMPARATOR = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem == newItem
        }
    }
}

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, item: Post?) {

    Log.d("PureBBS", "loadImage .... ${item?.toString()}")

    if (item != null) {
        val path = Config.calcAvatarPath(
            source = item.source,
            avatarFileName = item.avatarFileName,
            oauth_avatarUrl = item.oauth?.avatarUrl,
            anonymous = item.anonymous,
            isMyself = false
        )
        Log.d("PureBBS", "image path: $path")
        Glide.with(view.context).load("${path}")
            .circleCrop()
            .into(view)
    }
}

