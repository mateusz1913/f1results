package dev.mateusz1913.f1results.repository.services

import dev.mateusz1913.f1results.createKtorClient
import dev.mateusz1913.f1results.repository.models.qualifying_results.QualifyingResultsResponse
import io.ktor.client.*
import io.ktor.client.request.*

class QualifyingResultsApi(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
) {
    suspend fun getSpecificQualifyingResult(
        season: String,
        round: String?,
        position: Int?
    ): QualifyingResultsResponse {
        var paramString = "/$season"
        if (round != null) {
            paramString += "/$round"
        }
        var positionString = ""
        if (position != null) {
            positionString += "/$position"
        }
        return client.get("$baseUrl$paramString/qualifying$positionString.json")
    }

    suspend fun getQualifyingResult(
        limit: Int?,
        offset: Int?,
        season: String?,
        circuitId: String?,
        constructorId: String?,
        driverId: Int?,
        grid: Int?,
        results: Int?,
        fastest: Int?,
        statusId: String?,
        position: Int?
    ): QualifyingResultsResponse {
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
        if (results != null) {
            paramString += "/results/$results"
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
        return client.get("$baseUrl$paramString/qualifying$positionString.json$queryString")
    }
}