package dev.mateusz1913.f1results.datasource.remote.race_results

import dev.mateusz1913.f1results.createKtorClient
import dev.mateusz1913.f1results.datasource.data.race_results.RaceResultsData
import dev.mateusz1913.f1results.datasource.data.race_results.RaceResultsResponse
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType
import io.ktor.client.*
import io.ktor.client.request.*

class RaceResultsApiImpl(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
): RaceResultsApi {
    override suspend fun getSpecificRaceResult(
        season: String,
        round: String,
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

    override suspend fun getRaceResults(
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