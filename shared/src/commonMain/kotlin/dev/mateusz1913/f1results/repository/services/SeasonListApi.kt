package dev.mateusz1913.f1results.repository.services

import dev.mateusz1913.f1results.createKtorClient
import dev.mateusz1913.f1results.repository.models.season_list.SeasonListResponse
import io.ktor.client.*
import io.ktor.client.request.*

class SeasonListApi(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
) {
    suspend fun getSeasonList(
        limit: Int?,
        offset: Int?,
        circuitId: String?,
        constructorId: String?,
        constructorStandings: String?,
        driverId: Int?,
        driverStandings: Int?,
        grid: Int?,
        fastest: Int?,
        results: Int?,
        statusId: String?,
        order: String?,
    ): SeasonListResponse {
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
        val response = client.get<SeasonListResponse>("$baseUrl$paramString/seasons.json$queryString")
        if (order == "desc") {
            response.MRData.SeasonTable.Seasons.reverse()
        }
        return response
    }
}