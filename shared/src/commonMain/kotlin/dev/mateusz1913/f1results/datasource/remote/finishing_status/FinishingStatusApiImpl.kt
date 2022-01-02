package dev.mateusz1913.f1results.datasource.remote.finishing_status

import dev.mateusz1913.f1results.createKtorClient
import dev.mateusz1913.f1results.datasource.data.finishing_status.FinishingStatusData
import dev.mateusz1913.f1results.datasource.data.finishing_status.FinishingStatusResponse
import io.ktor.client.*
import io.ktor.client.request.*

class FinishingStatusApiImpl(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
): FinishingStatusApi {
    override suspend fun getFinishingStatusList(
        limit: Int?,
        offset: Int?,
        season: String?,
        round: String?,
        circuitId: String?,
        constructorId: String?,
        driverId: String?,
        grid: Int?,
        results: Int?,
        fastest: Int?,
        statusId: String?,
    ): FinishingStatusData? {
        val queryString = "?limit=${limit ?: 30}&offset=${offset ?: 0}"
        var paramString = ""
        if (season != null) {
            paramString += "/$season"
        }
        if (round != null) {
            paramString += "/$round"
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
        if (results != null) {
            paramString += "/results/$results"
        }
        if (fastest != null) {
            paramString += "/fastest/$fastest"
        }
        if (statusId != null) {
            paramString += "/status/$statusId"
        }
        return try {
            val response = client.get<FinishingStatusResponse>("$baseUrl$paramString/drivers.json$queryString")
            response.data
        } catch (e: Exception) {
            return null
        }
    }
}