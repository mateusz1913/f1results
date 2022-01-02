package dev.mateusz1913.f1results.datasource.remote.lap_times

import dev.mateusz1913.f1results.datasource.remote.createKtorClient
import dev.mateusz1913.f1results.datasource.data.lap_times.LapTimesResponse
import dev.mateusz1913.f1results.datasource.data.lap_times.RaceWithLapTimesType
import io.ktor.client.*
import io.ktor.client.request.*

class LapTimesApiImpl(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
): LapTimesApi {
    override suspend fun getSpecificLap(
        limit: Int?,
        offset: Int?,
        season: String,
        round: String,
        lap: Int,
        driverId: String?,
    ): RaceWithLapTimesType? {
        val queryString = "?limit=${limit ?: 30}&offset=${offset ?: 0}"
        var paramString = "/$season/$round"
        if (driverId != null) {
            paramString += "/drivers/$driverId"
        }
        try {
            val response = client.get<LapTimesResponse>("$baseUrl/$paramString/laps/$lap.json$queryString")
            if (response.data.raceTable.races.isEmpty()) {
                return null
            }
            return response.data.raceTable.races[0]
        } catch (e: Exception) {
            return null
        }
    }
}