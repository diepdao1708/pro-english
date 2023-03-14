package com.example.proenglish.data.models

import com.google.gson.annotations.SerializedName

data class DictionaryData(
    val word: String,
    val phonetics: List<Phonetic>,
    val meanings: List<Meaning>,
)

data class Phonetic(
    val text: String?,
    val audio: String,
)

data class Meaning(
    @SerializedName("partOfSpeech")
    val partOfSpeech: String,
    val definitions: List<Definition>,
)

data class Definition(
    val definition: String,
    val example: String?,
)