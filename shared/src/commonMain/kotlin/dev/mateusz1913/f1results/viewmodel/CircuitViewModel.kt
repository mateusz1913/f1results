package dev.mateusz1913.f1results.viewmodel

import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.repository.circuit.CircuitRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CircuitViewModel(
    private val circuitRepository: CircuitRepository,
    private val circuitId: String
) : BaseViewModel() {
    private val _circuitState =
        MutableStateFlow(CircuitState(circuit = circuitRepository.getCachedCircuit(circuitId)))
    val circuitState: StateFlow<CircuitState>
        get() = _circuitState
    @Suppress("unused")
    fun observeCircuit(onChange: (CircuitState) -> Unit) {
        circuitState.observe(onChange)
    }

    init {
        if (circuitState.value.circuit == null) {
            fetchCircuit()
        }
    }

    fun fetchCircuit() {
        _circuitState.update { it.copy(isFetching = true) }
        coroutineScope.launch {
            val circuit = circuitRepository.fetchCircuit(circuitId)
            _circuitState.update { it.copy(circuit = circuit, isFetching = false) }
        }
    }

    data class CircuitState(
        val circuit: CircuitType? = null,
        val isFetching: Boolean = false
    )
}
