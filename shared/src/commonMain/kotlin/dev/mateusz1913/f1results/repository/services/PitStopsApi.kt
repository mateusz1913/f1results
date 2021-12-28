package dev.mateusz1913.f1results.repository.services

import dev.mateusz1913.f1results.createKtorClient
import dev.mateusz1913.f1results.repository.models.pitstops.PitStopsResponse
import io.ktor.client.*
import io.ktor.client.request.*

class PitStopsApi(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
) {
    suspend fun getPitStops(
        limit: Int?,
        offset: Int?,
        season: String,
        round: Int,
        pitStopCount: Int?,
        driverId: String?,
        lap: Int?,
    ): PitStopsResponse {
        val queryString = "?limit=${limit ?: 30}&offset=${offset ?: 0}"
        var paramString = "/$season/$round"
        if (driverId != null) {
            paramString += "/drivers/$driverId"
        }
        if (lap != null) {
            paramString += "/laps/$lap"
        }
        var positionString = ""
        if (pitStopCount != null) {
            positionString += "/$pitStopCount"
        }
        return client.get("$baseUrl$paramString/pitstops$positionString.json$queryString")
    }
}