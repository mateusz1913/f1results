package dev.mateusz1913.f1results.datasource.remote.season_list

import dev.mateusz1913.f1results.createKtorClient
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonListResponse
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonListData
import io.ktor.client.*
import io.ktor.client.request.*

class SeasonListApiImpl(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
): SeasonListApi {
    override suspend fun getSeasonList(
        limit: Int?,
        offset: Int?,
        circuitId: String?,
        constructorId: String?,
        constructorStandings: Int?,
        driverId: String?,
        driverStandings: Int?,
        grid: Int?,
        fastest: Int?,
        results: Int?,
        statusId: String?,
        order: String?,
    ): SeasonListData? {
        val queryString = "?limit=${limit ?: 30}&offset=${offset ?: 0}"
        var paramString = ""
        if (circuitId != null) {
            paramString += "/circuits/$circuitId"
        }
        if (constructorId != null) {
            paramString += "/constructors/$constructorId"
            if (constructorStandings != null) {
                paramString += "/constructorStandings/$constructorStandings"
            }
        }
        if (driverId != null) {
            paramString += "/drivers/$driverId"
            if (driverStandings != null) {
                paramString += "/driverStandings/$driverStandings"
            }
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
            val response = client.get<SeasonListResponse>("$baseUrl$paramString/seasons.json$queryString")
            if (order == "desc") {
                response.data.seasonTable.seasons.reverse()
            }
            response.data
        } catch (e: Exception) {
            null
        }
    }
}