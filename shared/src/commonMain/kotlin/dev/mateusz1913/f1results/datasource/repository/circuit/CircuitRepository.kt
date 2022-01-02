package dev.mateusz1913.f1results.datasource.repository.circuit

import dev.mateusz1913.f1results.datasource.data.circuit.CircuitInfoData
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.remote.circuit.CircuitsApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CircuitRepository: KoinComponent {
    private val circuitsApi: CircuitsApi by inject()

    suspend fun fetchCircuit(circuitId: String): CircuitType? {
        return circuitsApi.getSpecificCircuit(circuitId)
    }

    suspend fun fetchCircuitList(
        limit: Int? = null,
        offset: Int? = null,
        season: String? = null,
        round: String? = null,
        constructorId: String? = null,
        driverId: String? = null,
        grid: Int? = null,
        results: Int? = null,
        fastest: Int? = null,
        statusId: String? = null,
    ): CircuitInfoData? {
        return circuitsApi.getCircuits(
            limit,
            offset,
            season,
            round,
            constructorId,
            driverId,
            grid,
            results,
            fastest,
            statusId
        )
    }
}