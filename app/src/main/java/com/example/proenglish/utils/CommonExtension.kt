package com.example.proenglish.utils

import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun LifecycleOwner.launchRepeatOnLifecycle(
    state: Lifecycle.State,
    block: suspend CoroutineScope.() -> Unit
): Job = lifecycleScope.launch {
    repeatOnLifecycle(state, block)
}

fun View.lifecycleOwner() = ViewTreeLifecycleOwner.get(this)