package dev.mateusz1913.f1results.repository.services

import dev.mateusz1913.f1results.createKtorClient
import dev.mateusz1913.f1results.repository.models.standings.StandingsResponse
import io.ktor.client.*
import io.ktor.client.request.*

class StandingsApi(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
) {
    suspend fun getSpecificDriverStandings(
        season: String,
        round: String?,
        position: Int?
    ): StandingsResponse {
        var paramString = "/$season"
        if (round != null) {
            paramString = "/$round"
        }
        var positionString = ""
        if (position != null) {
            positionString += "/$position"
        }
        return client.get("$baseUrl$paramString/driverStandings$positionString.json")
    }

    suspend fun getSpecificConstructorStandings(
        season: String,
        round: String?,
        position: Int?
    ): StandingsResponse {
        var paramString = "/$season"
        if (round != null) {
            paramString = "/$round"
        }
        var positionString = ""
        if (position != null) {
            positionString += "/$position"
        }
        return client.get("$baseUrl$paramString/constructorStandings$positionString.json")
    }

    suspend fun getDriverStandings(
        limit: Int?,
        offset: Int?,
        season: String?,
        driverId: String?,
        position: String?,
    ): StandingsResponse {
        val queryString = "?limit=${limit ?: 30}&offset=${offset ?: 0}"
        var paramString = ""
        if (season != null) {
            paramString += "/$season"
        }
        if (driverId != null) {
            paramString += "/drivers/$driverId"
        }
        var positionString = ""
        if (position != null) {
            positionString += "/$position"
        }
        return client.get("$baseUrl$paramString/driverStandings$positionString.json$queryString")
    }

    suspend fun getConstructorStandings(
        limit: Int?,
        offset: Int?,
        season: String?,
        constructorId: String?,
        position: String?,
    ): StandingsResponse {
        val queryString = "?limit=${limit ?: 30}&offset=${offset ?: 0}"
        var paramString = ""
        if (season != null) {
            paramString += "/$season"
        }
        if (constructorId != null) {
            paramString += "/constructors/$constructorId"
        }
        var positionString = ""
        if (position != null) {
            positionString += "/$position"
        }
        return client.get("$baseUrl$paramString/constructorStandings$positionString.json$queryString")
    }
}