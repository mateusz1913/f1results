package dev.mateusz1913.f1results.datasource.remote.circuit

import dev.mateusz1913.f1results.datasource.remote.createKtorClient
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitInfoData
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitResponse
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import io.ktor.client.*
import io.ktor.client.request.*

class CircuitsApiImpl(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
): CircuitsApi {
    override suspend fun getSpecificCircuit(circuitId: String): CircuitType? {
        try {
            val response = client.get<CircuitResponse>("$baseUrl/circuits/$circuitId.json")
            if (response.data.circuitTable.circuits.isEmpty()) {
                return null
            }
            return response.data.circuitTable.circuits[0]
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun getCircuits(
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
    ): CircuitInfoData? {
        val queryString = "?limit=${limit ?: 30}&offset=${offset ?: 0}"
        var paramString = ""
        if (season != null) {
            paramString += "/$season"
        }
        if (round != null) {
            paramString += "/$round"
        }
        if (constructorId != null) {
            paramString += "/constructors/$constructorId"
        }
        if (driverId != null) {
            paramString += "/drivers/$driverId"
        }
        if (grid != null) {
            paramString += "/grid/$grid"
        }
        if (results != null) {
            paramString += "/results/$results"
        }
        if (fastest != null) {
            paramString += "/fastest/$fastest"
        }
        if (statusId != null) {
            paramString += "/status/$statusId"
        }
        return try {
            val response = client.get<CircuitResponse>("$baseUrl$paramString/circuits.json$queryString")
            response.data
        } catch (e: Exception) {
            null
        }
    }
}