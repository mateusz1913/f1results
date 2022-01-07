package dev.mateusz1913.f1results.datasource.cache.circuit

import dev.mateusz1913.f1results.datasource.cache.Circuit_Entity
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType

interface CircuitCache {
    fun get(circuitId: String): Circuit_Entity
    fun insert(circuit: CircuitType)
    fun insert(circuits: Array<CircuitType>)
}