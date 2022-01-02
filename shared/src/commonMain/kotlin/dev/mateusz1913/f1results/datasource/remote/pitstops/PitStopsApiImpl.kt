package dev.mateusz1913.f1results.datasource.remote.pitstops

import dev.mateusz1913.f1results.datasource.remote.createKtorClient
import dev.mateusz1913.f1results.datasource.data.pitstops.PitStopsResponse
import dev.mateusz1913.f1results.datasource.data.pitstops.RaceWithPitStopsType
import io.ktor.client.*
import io.ktor.client.request.*

class PitStopsApiImpl(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
): PitStopsApi {
    override suspend fun getPitStops(
        limit: Int?,
        offset: Int?,
        season: String,
        round: String,
        pitStopCount: Int?,
        driverId: String?,
        lap: Int?,
    ): RaceWithPitStopsType? {
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
        try {
            val response = client.get<PitStopsResponse>("$baseUrl$paramString/pitstops$positionString.json$queryString")
            if (response.data.raceTable.races.isEmpty()) {
                return null
            }
            return response.data.raceTable.races[0]
        } catch (e: Exception) {
            return null
        }
    }
}