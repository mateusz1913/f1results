package dev.mateusz1913.f1results.repository.services

import dev.mateusz1913.f1results.createKtorClient
import dev.mateusz1913.f1results.repository.models.race_results.RaceResultsResponse
import io.ktor.client.*
import io.ktor.client.request.*

class RaceResultsApi(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
) {
    suspend fun getSpecificRaceResult(
        season: String,
        round: String?,
        position: Int?
    ): RaceResultsResponse {
        var paramString = "/$season"
        if (round != null) {
            paramString += "/$round"
        }
        var positionString = ""
        if (position != null) {
            positionString += "/$position"
        }
        return client.get("$baseUrl$paramString/results$positionString.json")
    }

    suspend fun getRaceResults(
        limit: Int?,
        offset: Int?,
        season: String?,
        circuitId: String?,
        constructorId: String?,
        driverId: Int?,
        grid: Int?,
        fastest: Int?,
        statusId: String?,
        position: Int?
    ): RaceResultsResponse {
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
        return client.get("$baseUrl$paramString/results$positionString.json$queryString")
    }
}