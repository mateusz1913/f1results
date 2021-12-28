package dev.mateusz1913.f1results.repository.services

import dev.mateusz1913.f1results.createKtorClient
import dev.mateusz1913.f1results.repository.models.race_results.RaceResultsResponse
import dev.mateusz1913.f1results.repository.models.race_results.RaceResultsData
import dev.mateusz1913.f1results.repository.models.race_results.RaceWithResultsType
import io.ktor.client.*
import io.ktor.client.request.*

class RaceResultsApi(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
) {
    suspend fun getSpecificRaceResult(
        season: String,
        round: Int,
        position: Int?
    ): RaceWithResultsType? {
        val paramString = "/$season/$round"
        var positionString = ""
        if (position != null) {
            positionString += "/$position"
        }
        try {
            val response = client.get<RaceResultsResponse>("$baseUrl$paramString/results$positionString.json")
            if (response.data.raceTable.races.isEmpty()) {
                return null
            }
            return response.data.raceTable.races[0]
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun getRaceResults(
        limit: Int?,
        offset: Int?,
        season: String?,
        circuitId: String?,
        constructorId: String?,
        driverId: String?,
        grid: Int?,
        fastest: Int?,
        statusId: String?,
        position: Int?
    ): RaceResultsData? {
        val queryString = "?limit=${limit ?: 30}&offset=${offset ?: 0}"
        var paramString = ""
        if (season != null) {
            paramString += "/$season"
        }
        if (circuitId != null) {
            paramString += "/circuits/$circuitId"
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
        if (fastest != null) {
            paramString += "/fastest/$fastest"
        }
        if (statusId != null) {
            paramString += "/status/$statusId"
        }
        var positionString = ""
        if (position != null) {
            positionString += "/$position"
        }
        return try {
            val response = client.get<RaceResultsResponse>("$baseUrl$paramString/results$positionString.json$queryString")
            response.data
        } catch (e: Exception) {
            return null
        }
    }
}