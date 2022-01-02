package dev.mateusz1913.f1results.datasource.remote.circuit

import dev.mateusz1913.f1results.datasource.data.circuit.CircuitInfoData
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType

interface CircuitsApi {
    suspend fun getSpecificCircuit(circuitId: String): CircuitType?

    suspend fun getCircuits(
        limit: Int?,
        offset: Int?,
        season: String?,
        round: String?,
        constructorId: String?,
        driverId: String?,
        grid: Int?,
        results: Int?,
        fastest: Int?,
        statusId: String?,
    ): CircuitInfoData?
}