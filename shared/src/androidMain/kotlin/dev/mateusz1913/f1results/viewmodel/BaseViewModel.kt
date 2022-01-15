package dev.mateusz1913.f1results.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

actual abstract class BaseViewModel: ViewModel() {
    protected actual val coroutineScope = viewModelScope

    actual fun dispose() {
        coroutineScope.cancel()
        onCleared()
    }

    protected actual fun <T> StateFlow<T>.observe(onChange: (T) -> Unit) {
        this.onEach { onChange(it) }.launchIn(coroutineScope)
    }

    protected actual override fun onCleared() {
        super.onCleared()
    }
}