package dev.mateusz1913.f1results.datasource.repository.circuit

import dev.mateusz1913.f1results.datasource.cache.circuit.CircuitCache
import dev.mateusz1913.f1results.datasource.cache.circuit.toCircuitType
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.RequestsTimestampsCache
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.getCircuitRequest
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitInfoData
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.remote.circuit.CircuitsApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class CircuitRepository(
    private val circuitsApi: CircuitsApi,
    private val circuitCache: CircuitCache,
    private val requestsTimestampsCache: RequestsTimestampsCache
) {
    suspend fun fetchCircuit(circuitId: String): CircuitType? {
        val circuit = circuitsApi.getSpecificCircuit(circuitId)
        if (circuit != null) {
            val succeeded = circuitCache.insert(circuit)
            if (succeeded) {
                requestsTimestampsCache.insertRequestTimestamp(
                    getCircuitRequest(circuitId),
                    now().toEpochMilliseconds()
                )
            }
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
            val succeeded = circuitCache.insert(circuitInfoData.circuitTable.circuits)
            if (succeeded) {
                circuitInfoData.circuitTable.circuits.forEach {
                    requestsTimestampsCache.insertRequestTimestamp(
                        getCircuitRequest(it.circuitId),
                        now().toEpochMilliseconds()
                    )
                }
            }
        }
        return circuitInfoData
    }

    fun getCachedCircuit(circuitId: String): CircuitType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(getCircuitRequest(circuitId))?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cached = circuitCache.get(circuitId)
            if (currentTimestamp > cached.timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cached.toCircuitType()
        } catch (e: Exception) {
            Napier.w("No cached circuit ${e.message}", e, "CircuitRepository")
            return null
        }
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}