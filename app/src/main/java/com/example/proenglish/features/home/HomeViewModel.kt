package com.example.proenglish.features.home

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proenglish.R
import com.example.proenglish.domain.repositories.PostRepository
import com.example.proenglish.utils.formatDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {

    sealed class Event {
        data class Navigate(val direction: Int, val bundle: Bundle) : Event()
    }

    companion object {
        const val POST_DATA = "PostData"
    }

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _events = Channel<Event>(capacity = Channel.UNLIMITED)
    val events = _events.receiveAsFlow()

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
                    _uiState.update { it.copy(messageResId = R.string.post_failure) }
                }
            delay(500)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun navigateToPostDetail(position: Int) {
        val postData = _uiState.value.posts[position]
        val args = bundleOf(POST_DATA to postData)
        _events.trySend(Event.Navigate(R.id.action_homeFragment_to_postDetailFragment, args))
    }
}