package com.example.proenglish.features.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proenglish.R
import com.example.proenglish.data.repositories.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DictionaryUiState())
    val uiState: StateFlow<DictionaryUiState> = _uiState.asStateFlow()

    fun takeMeaning(word: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            dictionaryRepository.takeMeaning(word = word)
                .onSuccess { dictionaries ->
                    if (dictionaries.isNotEmpty()) {
                        _uiState.update { Convert.convertToDictionaryUiState(dictionaries[0]) }
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(messageResId = R.string.dictionary_failure) }
                }
            delay(500)
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}