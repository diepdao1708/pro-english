package com.example.proenglish.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proenglish.R
import com.example.proenglish.domain.repositories.PostRepository
import com.example.proenglish.utils.formatDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun getPosts() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            postRepository.getPosts()
                .onSuccess { listPostData ->
                    _uiState.update {
                        it.copy(posts = listPostData.posts.map { postData ->
                            PostItemUiState(
                                postData.title,
                                postData.description,
                                postData.content,
                                postData.createdAt.formatDate(),
                            )
                        })
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(messageResId = R.string.failure) }
                }
            delay(500)
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}