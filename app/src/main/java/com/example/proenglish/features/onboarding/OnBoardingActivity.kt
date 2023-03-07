package com.example.proenglish.features.onboarding

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.proenglish.R
import com.example.proenglish.databinding.ActivityOnBoardingBinding
import com.example.proenglish.features.login.LoginActivity
import com.example.proenglish.features.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private val viewModel: OnBoardingViewModel by viewModels()
    private val onBoardingAdapter: OnBoardingAdapter by lazy {
        OnBoardingAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
        setContentView(binding.root)

        binding.viewModel = viewModel

        setUpIndicators()
        setCurrentIndicator(0)
        binding.onboardViewPage.apply {
            adapter = onBoardingAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setCurrentIndicator(position)
                }
            })
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        binding.nextButton.setOnClickListener {
            if (binding.onboardViewPage.currentItem + 1 < viewModel.uiState.value.onBoardings.size) {
                binding.onboardViewPage.currentItem += 1

            } else {
                navigateToRegister()
            }
        }

        lifecycleScope.apply {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.events.collect { event ->
                        when (event) {
                            is OnBoardingViewModel.Event.NavigateToRegister -> navigateToRegister()
                            is OnBoardingViewModel.Event.NavigateToLogin -> navigateToLogin()
                        }
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect { onBoardingUiState ->
                        onBoardingAdapter.reloadData(onBoardingUiState.onBoardings)
                    }
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setUpIndicators() {
        val indicators = arrayOfNulls<ImageView>(viewModel.uiState.value.onBoardings.size)
        val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let { imageView ->
                imageView.setImageDrawable(getDrawable(R.drawable.background_indicator_inactive))
                imageView.layoutParams = layoutParams
                binding.indicatorsLinearLayout.addView(imageView)
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setCurrentIndicator(position: Int) {
        val childCount = binding.indicatorsLinearLayout.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorsLinearLayout.getChildAt(i) as ImageView
            imageView.setImageDrawable(
                getDrawable(
                    when (position) {
                        i -> R.drawable.background_indicator_active
                        else -> R.drawable.background_indicator_inactive
                    }
                )
            )
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}