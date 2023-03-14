package com.example.proenglish.features.dictionary

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proenglish.databinding.ItemDefinitionBinding
import com.example.proenglish.utils.lifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow

class DefinitionAdapter(
    private var definitions: List<DefinitionItemUiState> = emptyList(),
) :
    RecyclerView.Adapter<DefinitionAdapter.DefinitionViewHolder>() {

    inner class DefinitionViewHolder(val binding: ItemDefinitionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val cachedItem = MutableStateFlow<DefinitionItemUiState?>(null)

        init {
            binding.item = cachedItem
        }

        fun bind(definitionItemUiState: DefinitionItemUiState) {
            cachedItem.value = definitionItemUiState
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        val binding =
            ItemDefinitionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = parent.lifecycleOwner()

        return DefinitionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return definitions.size
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        holder.bind(definitions[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadData(definitions: List<DefinitionItemUiState>) {
        this.definitions = definitions
        notifyDataSetChanged()
    }
}