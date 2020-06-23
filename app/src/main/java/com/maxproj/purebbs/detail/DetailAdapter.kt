package com.maxproj.purebbs.detail

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
import com.maxproj.purebbs.databinding.DetailItemViewBinding


class DetailAdapter : PagedListAdapter<Detail, DetailAdapter.DetailItemViewHolder>(DETAIL_COMPARATOR) {

    lateinit var viewModel: DetailViewModel
    lateinit var lifecycleOwner: LifecycleOwner

    override fun onBindViewHolder(holder: DetailItemViewHolder, position: Int) {
        Log.d("PureBBS", "adapter onBindViewHolder")
        val item = getItem(position)
        Log.d("PureBBS", "adapter onBindViewHolder item: $item")
        holder.bind(item!!, viewModel)
//        holder.user.text = item.user
//        holder.title.text = item.detailTitle
        Log.d("PureBBS", "adapter onBindViewHolder: $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailItemViewHolder {

        val binding =
            DetailItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.lifecycleOwner = lifecycleOwner

        return DetailItemViewHolder(binding)
    }

    class DetailItemViewHolder(val binding: DetailItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Detail, viewModel: DetailViewModel) {
            binding.viewModel = viewModel
            binding.item = item
            binding.executePendingBindings() //
        }
    }

    companion object {
        private val DETAIL_COMPARATOR = object : DiffUtil.ItemCallback<Detail>() {
            override fun areItemsTheSame(oldItem: Detail, newItem: Detail): Boolean =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: Detail, newItem: Detail): Boolean =
                oldItem == newItem
        }
    }
}

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, item: Detail?) {

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