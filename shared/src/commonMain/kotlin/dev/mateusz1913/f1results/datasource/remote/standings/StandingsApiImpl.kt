package dev.mateusz1913.f1results.datasource.remote.standings

import dev.mateusz1913.f1results.datasource.remote.createKtorClient
import dev.mateusz1913.f1results.datasource.data.standings.*
import io.ktor.client.*
import io.ktor.client.request.*

class StandingsApiImpl(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
): StandingsApi {
    override suspend fun getSpecificDriverStandings(
        season: String,
        round: String,
        position: Int?
    ): Array<DriverStandingsType>? {
        val paramString = "/$season/$round"
        var positionString = ""
        if (position != null) {
            positionString += "/$position"
        }
        return try {
            val response = client.get<DriverStandingsResponse>("$baseUrl$paramString/driverStandings$positionString.json")
            response.data.standingsTable.standingsLists
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getSpecificConstructorStandings(
        season: String,
        round: String,
        position: Int?
    ): Array<ConstructorStandingsType>? {
        val paramString = "/$season/$round"
        var positionString = ""
        if (position != null) {
            positionString += "/$position"
        }
        return try {
            val response = client.get<ConstructorStandingsResponse>("$baseUrl$paramString/constructorStandings$positionString.json")
            response.data.standingsTable.standingsLists
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getDriverStandings(
        limit: Int?,
        offset: Int?,
        season: String?,
        driverId: String?,
        position: String?,
    ): DriverStandingsData? {
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
        return try {
            val response = client.get<DriverStandingsResponse>("$baseUrl$paramString/driverStandings$positionString.json$queryString")
            response.data
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getConstructorStandings(
        limit: Int?,
        offset: Int?,
        season: String?,
        constructorId: String?,
        position: String?,
    ): ConstructorStandingsData? {
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
        return try {
            val response = client.get<ConstructorStandingsResponse>("$baseUrl$paramString/constructorStandings$positionString.json$queryString")
            response.data
        } catch (e: Exception) {
            null
        }
    }
}