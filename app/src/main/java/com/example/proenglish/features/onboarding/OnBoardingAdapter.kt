package com.example.proenglish.features.onboarding

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proenglish.databinding.OnboardingItemBinding
import com.example.proenglish.utils.lifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow

class OnBoardingAdapter(
    private var onBoardings: List<OnBoardingData> = emptyList(),
) : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    inner class OnBoardingViewHolder(val binding: OnboardingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val cachedItem = MutableStateFlow<OnBoardingData?>(null)

        init {
            binding.item = cachedItem
        }

        fun bind(onBoardingData: OnBoardingData) {
            cachedItem.value = onBoardingData
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val binding =
            OnboardingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = parent.lifecycleOwner()
        return OnBoardingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return onBoardings.size
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(onBoardings[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadData(onBoardings: List<OnBoardingData>) {
        this.onBoardings = onBoardings
        notifyDataSetChanged()
    }
}