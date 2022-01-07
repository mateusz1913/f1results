package dev.mateusz1913.f1results.android.presentation.circuit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mateusz1913.f1results.datasource.repository.circuit.CircuitRepository
import kotlinx.coroutines.launch

class CircuitViewModel(
    private val circuitRepository: CircuitRepository,
    private val circuitId: String
): ViewModel() {
    var circuit by mutableStateOf(circuitRepository.getCachedCircuit(circuitId))
        private set

    var isFetchingCircuit by mutableStateOf(false)
        private set

    init {
        if (circuit == null) {
            fetchCircuit()
        }
    }

    fun fetchCircuit() {
        isFetchingCircuit = true
        viewModelScope.launch {
            circuit = circuitRepository.fetchCircuit(circuitId)
            isFetchingCircuit = false
        }
    }
}