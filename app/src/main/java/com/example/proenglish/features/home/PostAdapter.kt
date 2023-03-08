package com.example.proenglish.features.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proenglish.databinding.PostItemBinding
import com.example.proenglish.utils.lifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow

class PostAdapter(
    private var posts: List<PostItemUiState> = emptyList(),
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val cachedItem = MutableStateFlow<PostItemUiState?>(null)

        init {
            binding.item = cachedItem
        }

        fun bind(postItemUiState: PostItemUiState) {
            cachedItem.value = postItemUiState
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = parent.lifecycleOwner()

        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadData(posts: List<PostItemUiState>) {
        this.posts = posts
        notifyDataSetChanged()
    }
}