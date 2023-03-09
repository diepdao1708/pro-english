package com.example.proenglish.features.home.postDetail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.proenglish.features.home.HomeViewModel.Companion.POST_DATA
import com.example.proenglish.features.home.PostItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PostItemUiState("", "", "", ""))
    val uiState: StateFlow<PostItemUiState> = _uiState.asStateFlow()

    fun getArgs(bundle: Bundle?) {
        val post = bundle?.get(POST_DATA) as PostItemUiState
        _uiState.update { post }
    }
}