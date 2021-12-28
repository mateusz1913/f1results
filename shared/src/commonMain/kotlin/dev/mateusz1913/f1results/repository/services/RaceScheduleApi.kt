package dev.mateusz1913.f1results.repository.services

import dev.mateusz1913.f1results.createKtorClient
import dev.mateusz1913.f1results.repository.models.race_schedule.RaceScheduleResponse
import dev.mateusz1913.f1results.repository.models.race_schedule.RaceScheduleData
import dev.mateusz1913.f1results.repository.models.race_schedule.RaceType
import io.ktor.client.*
import io.ktor.client.request.*

class RaceScheduleApi(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
) {
    suspend fun getSpecificRaceSchedule(
        season: String,
        round: Int,
    ): RaceType? {
        val paramString = "/$season/$round"
        try {
            val response = client.get<RaceScheduleResponse>("$baseUrl$paramString.json")
            if (response.data.raceTable.races.isEmpty()) {
                return null
            }
            return response.data.raceTable.races[0]
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun getRaceSchedules(
        limit: Int?,
        offset: Int?,
        season: String?,
        circuitId: String?,
        constructorId: String?,
        driverId: String?,
        grid: Int?,
        fastest: Int?,
        results: Int?,
        statusId: String?,
    ): RaceScheduleData? {
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
        return try {
            val response = client.get<RaceScheduleResponse>("$baseUrl$paramString/races.json$queryString")
            response.data
        } catch (e: Exception) {
            return null
        }
    }
}