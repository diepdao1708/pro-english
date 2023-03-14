package com.example.proenglish.features.dictionary

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proenglish.databinding.ItemMeaningBinding
import com.example.proenglish.utils.lifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow

class MeaningAdapter(
    private var meanings: List<MeaningItemUiState> = emptyList(),
) : RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {

    inner class MeaningViewHolder(val binding: ItemMeaningBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val cachedItem = MutableStateFlow<MeaningItemUiState?>(null)
        private val definitionAdapter: DefinitionAdapter by lazy {
            DefinitionAdapter()
        }

        init {
            binding.item = cachedItem
            binding.definitionRecyclerView.apply {
                adapter = definitionAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }

        fun bind(meaningItemUiState: MeaningItemUiState) {
            cachedItem.value = meaningItemUiState
            definitionAdapter.reloadData(meaningItemUiState.definitions)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding = ItemMeaningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = parent.lifecycleOwner()

        return MeaningViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return meanings.size
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.bind(meanings[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadData(meanings: List<MeaningItemUiState>) {
        this.meanings = meanings
        notifyDataSetChanged()
    }
}