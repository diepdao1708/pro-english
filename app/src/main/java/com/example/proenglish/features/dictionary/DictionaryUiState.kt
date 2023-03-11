package com.example.proenglish.features.dictionary

data class DictionaryUiState(
    val isLoading: Boolean = false,
    val messageResId: Int? = null,
    val word: String = "",
    val uk: PhoneticItemUiState? = null,
    val us: PhoneticItemUiState? = null,
    val meanings: List<MeaningItemUiState> = emptyList(),
)

data class PhoneticItemUiState(
    val text: String?,
    val audio: String,
)

data class MeaningItemUiState(
    val partOfSpeech: String,
    val definitions: List<DefinitionItemUiState>,
)

data class DefinitionItemUiState(
    val definition: String,
    val example: String?,
)
