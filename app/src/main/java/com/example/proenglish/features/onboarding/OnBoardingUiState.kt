package com.example.proenglish.features.onboarding

import com.example.proenglish.R

data class OnBoardingUiState(
    val onBoardings: List<OnBoardingData>,
)

// TODO
internal val onBoardings = listOf(
    OnBoardingData(
        imageResId = R.drawable.home_outline,
        title = "Manage you task",
        description = "Organize all your to do's an projects. Color tag them to set priorities an categories."
    ),
    OnBoardingData(
        imageResId = R.drawable.home_outline,
        title = "Manage you task",
        description = "Organize all your to do's an projects. Color tag them to set priorities an categories."
    ),
    OnBoardingData(
        imageResId = R.drawable.home_outline,
        title = "Manage you task",
        description = "Organize all your to do's an projects. Color tag them to set priorities an categories."
    ),
    OnBoardingData(
        imageResId = R.drawable.home_outline,
        title = "Manage you task",
        description = "Organize all your to do's an projects. Color tag them to set priorities an categories."
    ),
)

data class OnBoardingData(
    val imageResId: Int,
    val title: String,
    val description: String,
)