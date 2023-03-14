package com.example.proenglish.features.user

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proenglish.databinding.ItemLeaderboardBinding
import com.example.proenglish.utils.lifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow

class LeaderboardAdapter(
    private var leaderboards: List<LeaderboardItemUiState> = emptyList(),
) : RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    inner class LeaderboardViewHolder(val binding: ItemLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val cachedItem = MutableStateFlow<LeaderboardItemUiState?>(null)

        init {
            binding.item = cachedItem
        }

        fun bind(leaderboardItemUiState: LeaderboardItemUiState) {
            cachedItem.value = leaderboardItemUiState
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val binding =
            ItemLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = parent.lifecycleOwner()

        return LeaderboardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return leaderboards.size
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        holder.bind(leaderboards[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadData(leaderboards: List<LeaderboardItemUiState>) {
        this.leaderboards = leaderboards
        notifyDataSetChanged()
    }
}