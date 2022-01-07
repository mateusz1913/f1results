package dev.mateusz1913.f1results.datasource.repository.circuit

import dev.mateusz1913.f1results.datasource.cache.circuit.CircuitCache
import dev.mateusz1913.f1results.datasource.cache.circuit.toCircuitType
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitInfoData
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.remote.circuit.CircuitsApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class CircuitRepository(private val circuitsApi: CircuitsApi, private val circuitCache: CircuitCache) {
    suspend fun fetchCircuit(circuitId: String): CircuitType? {
        val circuit = circuitsApi.getSpecificCircuit(circuitId)
        if (circuit != null) {
            circuitCache.insert(circuit)
        }
        return circuit
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
        val circuitInfoData = circuitsApi.getCircuits(
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
        if (circuitInfoData != null) {
            circuitCache.insert(circuitInfoData.circuitTable.circuits)
        }
        return circuitInfoData
    }

    fun getCachedCircuit(circuitId: String): CircuitType? {
        val cachedCircuit = try {
            val cached = circuitCache.get(circuitId)
            val currentTimestamp = now().toEpochMilliseconds()
            if (currentTimestamp < cached.timestamp + TIMESTAMP_THRESHOLD) {
                cached
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached circuit", tag = "CircuitRepository")
            null
        }
        return cachedCircuit?.toCircuitType()
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}