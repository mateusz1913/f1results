package dev.mateusz1913.f1results.datasource.cache.circuit

import dev.mateusz1913.f1results.datasource.cache.Circuit_Entity
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.data.circuit.LocationType

fun Circuit_Entity.toCircuitType(): CircuitType {
    return CircuitType(
        circuitId = circuit_id,
        url = url,
        circuitName = circuit_name,
        location = LocationType(
            country = country,
            locality = locality,
            alt = alt,
            lat = lat,
            long = long
        )
    )
}
