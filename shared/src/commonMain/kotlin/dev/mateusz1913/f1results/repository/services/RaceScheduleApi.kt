package dev.mateusz1913.f1results.repository.services

import dev.mateusz1913.f1results.createKtorClient
import dev.mateusz1913.f1results.repository.models.race_schedule.RaceScheduleResponse
import io.ktor.client.*
import io.ktor.client.request.*

class RaceScheduleApi(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
) {
    suspend fun getSpecificRaceSchedule(
        season: String,
        round: String?
    ): RaceScheduleResponse {
        var paramString = "/$season"
        if (round != null) {
            paramString += "/$round"
        }
        return client.get("$baseUrl$paramString.json")
    }

    suspend fun getRaceSchedules(
        limit: Int?,
        offset: Int?,
        season: String?,
        circuitId: String?,
        constructorId: String?,
        driverId: Int?,
        grid: Int?,
        fastest: Int?,
        results: Int?,
        statusId: String?,
    ): RaceScheduleResponse {
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
        if (results != null) {
            paramString += "/results/$results"
        }
        if (statusId != null) {
            paramString += "/status/$statusId"
        }
        return client.get("$baseUrl$paramString/races.json$queryString")
    }
}