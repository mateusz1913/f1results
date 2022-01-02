package dev.mateusz1913.f1results.datasource.remote.qualifying_results

import dev.mateusz1913.f1results.datasource.remote.createKtorClient
import dev.mateusz1913.f1results.datasource.data.qualifying_results.QualifyingResultsResponse
import dev.mateusz1913.f1results.datasource.data.qualifying_results.QualifyingResultsData
import dev.mateusz1913.f1results.datasource.data.qualifying_results.RaceWithQualifyingResultsType
import io.ktor.client.*
import io.ktor.client.request.*

class QualifyingResultsApiImpl(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
): QualifyingResultsApi {
    override suspend fun getSpecificQualifyingResult(
        season: String,
        round: String,
        position: Int?
    ): RaceWithQualifyingResultsType? {
        val paramString = "/$season/$round"
        var positionString = ""
        if (position != null) {
            positionString += "/$position"
        }
        try {
            val response = client.get<QualifyingResultsResponse>("$baseUrl$paramString/qualifying$positionString.json")
            if (response.data.raceTable.races.isEmpty()) {
                return null
            }
            return response.data.raceTable.races[0]
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun getQualifyingResult(
        limit: Int?,
        offset: Int?,
        season: String?,
        circuitId: String?,
        constructorId: String?,
        driverId: String?,
        grid: Int?,
        results: Int?,
        fastest: Int?,
        statusId: String?,
        position: Int?
    ): QualifyingResultsData? {
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
        return try {
            val response = client.get<QualifyingResultsResponse>("$baseUrl$paramString/qualifying$positionString.json$queryString")
            response.data
        } catch (e: Exception) {
            return null
        }
    }
}