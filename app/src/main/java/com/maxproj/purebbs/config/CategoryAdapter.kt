package com.maxproj.purebbs.config

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maxproj.purebbs.databinding.CategoryItemViewBinding
import com.maxproj.purebbs.post.PostViewModel


class CategoryAdapter : ListAdapter<Config.Category, CategoryAdapter.CategoryItemViewHolder>(CATEGORY_COMPARATOR) {

    lateinit var viewModel: ConfigViewModel
    lateinit var lifecycleOwner: LifecycleOwner

    override fun onCurrentListChanged(
        previousList: MutableList<Config.Category>,
        currentList: MutableList<Config.Category>
    ) {
        Log.d("PureBBS", "CategoryAdapter onCurrentListChanged: $previousList")
        Log.d("PureBBS", "CategoryAdapter onCurrentListChanged: $currentList")
        super.onCurrentListChanged(previousList, currentList)
    }
    override fun getItemCount(): Int {
        val count = super.getItemCount()
        Log.d("PureBBS", "CategoryAdapter getItemCount: $count")
        return count
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        Log.d("PureBBS", "CategoryAdapter onBindViewHolder")
        val item = getItem(position)
        Log.d("PureBBS", "CategoryAdapter onBindViewHolder item: $item")
        holder.bind(item!!, viewModel)
//        holder.user.text = item.user
//        holder.title.text = item.postTitle
        Log.d("PureBBS", "CategoryAdapter onBindViewHolder: $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        Log.d("PureBBS", "CategoryAdapter onCreateViewHolder")
        val binding =
            CategoryItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.lifecycleOwner = lifecycleOwner

        return CategoryItemViewHolder(binding)
    }

    class CategoryItemViewHolder(val binding: CategoryItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            Log.d("PureBBS", "CategoryAdapter onCreateViewHolder init")
        }
        fun bind(item: Config.Category, viewModel:ConfigViewModel) {
            binding.viewModel = viewModel
            binding.item = item
            binding.executePendingBindings() //
        }
    }
    companion object {
        private val CATEGORY_COMPARATOR = object : DiffUtil.ItemCallback<Config.Category>() {
            override fun areItemsTheSame(oldItem: Config.Category, newItem: Config.Category): Boolean =
                oldItem.idStr == newItem.idStr

            override fun areContentsTheSame(oldItem: Config.Category, newItem: Config.Category): Boolean =
                oldItem == newItem
        }
    }
}
