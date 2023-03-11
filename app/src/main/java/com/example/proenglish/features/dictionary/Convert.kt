package com.example.proenglish.features.dictionary

import com.example.proenglish.domain.models.DictionaryData
import com.example.proenglish.domain.models.Meaning
import com.example.proenglish.domain.models.Phonetic

object Convert {
    fun convertToDictionaryUiState(dictionary: DictionaryData): DictionaryUiState {
        return DictionaryUiState(
            word = dictionary.word,
            uk = findPhonetic(dictionary.phonetics, "-uk"),
            us = findPhonetic(dictionary.phonetics, "-us"),
            meanings = convertToListMeaningItemUiState(dictionary.meanings),
        )
    }

    private fun findPhonetic(phonetics: List<Phonetic>, regex: String): PhoneticItemUiState? {
        val phonetic = phonetics.find { it.audio.contains(Regex(regex)) } ?: return null
        return PhoneticItemUiState(phonetic.text, phonetic.audio)
    }

    private fun convertToListMeaningItemUiState(meanings: List<Meaning>): List<MeaningItemUiState> {
        return meanings.filter { it.partOfSpeech.isNotEmpty() }.map { meaning ->
            MeaningItemUiState(
                partOfSpeech = meaning.partOfSpeech,
                definitions = meaning.definitions.map {
                    DefinitionItemUiState(
                        definition = it.definition,
                        example = it.example,
                    )
                }
            )
        }
    }
}