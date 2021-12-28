package dev.mateusz1913.f1results.repository.services

import dev.mateusz1913.f1results.createKtorClient
import dev.mateusz1913.f1results.repository.models.constructor.ConstructorInfoData
import dev.mateusz1913.f1results.repository.models.constructor.ConstructorResponse
import dev.mateusz1913.f1results.repository.models.constructor.ConstructorType
import io.ktor.client.*
import io.ktor.client.request.*

class ConstructorsApi(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
) {
    suspend fun getSpecificConstructor(constructorId: String): ConstructorType? {
        try {
            val response = client.get<ConstructorResponse>("$baseUrl/constructors/$constructorId.json")
            if (response.data.constructorTable.constructors.isEmpty()) {
                return null
            }
            return response.data.constructorTable.constructors[0]
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun getConstructors(
        limit: Int?,
        offset: Int?,
        season: String?,
        round: Int?,
        circuitId: String?,
        constructorStandings: Int?,
        driverId: String?,
        grid: Int?,
        results: Int?,
        fastest: Int?,
        statusId: String?,
    ): ConstructorInfoData? {
        val queryString = "?limit=${limit ?: 30}&offset=${offset ?: 0}"
        var paramString = ""
        if (season != null) {
            paramString += "/$season"
        }
        if (round != null) {
            paramString += "/$round"
        }
        if (circuitId != null) {
            paramString += "/circuits/$circuitId"
        }
        if (constructorStandings != null) {
            paramString += "/constructorStandings/$constructorStandings"
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
            val response = client.get<ConstructorResponse>("$baseUrl$paramString/constructors.json$queryString")
            response.data
        } catch (e: Exception) {
            return null
        }
    }
}