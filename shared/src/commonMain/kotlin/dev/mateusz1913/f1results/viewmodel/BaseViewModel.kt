package dev.mateusz1913.f1results.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

expect abstract class BaseViewModel() {
    protected val coroutineScope: CoroutineScope

    fun dispose()

    protected fun <T> StateFlow<T>.observe(onChange: (T) -> Unit)

    protected open fun onCleared()
}
