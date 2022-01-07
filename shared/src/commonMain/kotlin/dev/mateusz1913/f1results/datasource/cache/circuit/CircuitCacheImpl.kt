package dev.mateusz1913.f1results.datasource.cache.circuit

import dev.mateusz1913.f1results.datasource.cache.CircuitQueries
import dev.mateusz1913.f1results.datasource.cache.Circuit_Entity
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds

class CircuitCacheImpl(private val queries: CircuitQueries): CircuitCache {
    override fun get(circuitId: String): Circuit_Entity {
        return queries.getCircuitById(circuitId).executeAsOne()
    }

    override fun insert(circuit: CircuitType) {
        queries.insertCircuit(
            circuit_id = circuit.circuitId,
            url = circuit.url,
            circuit_name = circuit.circuitName,
            country = circuit.location.country,
            locality = circuit.location.locality,
            alt = circuit.location.alt,
            lat = circuit.location.lat,
            long = circuit.location.long,
            timestamp = now().toEpochMilliseconds()
        )
    }

    override fun insert(circuits: Array<CircuitType>) {
        circuits.forEach { circuit ->
            insert(circuit)
        }
    }
}