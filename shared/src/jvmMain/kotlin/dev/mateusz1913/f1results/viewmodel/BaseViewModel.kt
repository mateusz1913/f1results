package dev.mateusz1913.f1results.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

actual abstract class BaseViewModel {
    actual val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    actual fun dispose() {
        coroutineScope.cancel()
        onCleared()
    }

    protected actual fun <T> StateFlow<T>.observe(onChange: (T) -> Unit) {
        this.onEach { onChange(it) }.launchIn(coroutineScope)
    }

    protected actual open fun onCleared() {}
}