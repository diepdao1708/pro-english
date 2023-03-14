package com.example.proenglish.features.exam

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import com.example.proenglish.R
import com.example.proenglish.domain.models.Level
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor() : ViewModel() {

    sealed class Event {
        data class Navigate(val direction: Int, val bundle: Bundle) : Event()
    }

    companion object {
        const val LEVEL_DATA = "LevelData"
    }

    private val _events = Channel<Event>(capacity = Channel.UNLIMITED)
    val events = _events.receiveAsFlow()

    fun navigateToQuestion(level: Level) {
        val args = bundleOf(LEVEL_DATA to level.value)
        _events.trySend(
            Event.Navigate(
                R.id.action_examFragment_to_questionFragment,
                args,
            )
        )
    }
}