package dev.mateusz1913.f1results.repository.services

import dev.mateusz1913.f1results.createKtorClient
import dev.mateusz1913.f1results.repository.models.lap_times.LapsResponse
import io.ktor.client.*
import io.ktor.client.request.*

class LapTimesApi(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
) {
    suspend fun getLap(
        limit: Int?,
        offset: Int?,
        season: String,
        round: Int,
        lap: Int,
        driverId: String?,
    ): LapsResponse {
        val queryString = "?limit=${limit ?: 30}&offset=${offset ?: 0}"
        var paramString = "/$season/$round"
        if (driverId != null) {
            paramString += "/drivers/$driverId"
        }
        return client.get("$baseUrl/$paramString/laps/$lap.json$queryString")
    }
}