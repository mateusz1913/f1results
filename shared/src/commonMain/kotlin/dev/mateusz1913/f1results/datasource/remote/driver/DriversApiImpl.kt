package dev.mateusz1913.f1results.datasource.remote.driver

import dev.mateusz1913.f1results.datasource.remote.createKtorClient
import dev.mateusz1913.f1results.datasource.data.driver.DriverInfoData
import dev.mateusz1913.f1results.datasource.data.driver.DriverResponse
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.request.*

class DriversApiImpl(
    private val client: HttpClient = createKtorClient(),
    private val baseUrl: String = "https://ergast.com/api/f1"
): DriversApi {
    override suspend fun getSpecificDriver(driverId: String): DriverType? {
        try {
            val response = client.get<DriverResponse>("$baseUrl/drivers/$driverId.json")
            if (response.data.driverTable.drivers.isEmpty()) {
                Napier.d("No fetched driver: $driverId", null, "DriverState")
                return null
            }
            Napier.d("Fetched driver", null, "DriverState")
            return response.data.driverTable.drivers[0]
        } catch (e: Exception) {
            Napier.d("Error when fetching driver: ${e.message}", e, "DriverState")
            return null
        }
    }

    override suspend fun getDrivers(
        limit: Int?,
        offset: Int?,
        season: String?,
        round: String?,
        circuitId: String?,
        constructorId: String?,
        driverStandings: Int?,
        grid: Int?,
        results: Int?,
        fastest: Int?,
        statusId: String?,
    ): DriverInfoData? {
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
        if (driverStandings != null) {
            paramString += "/driverStandings/$driverStandings"
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
            val response = client.get<DriverResponse>("$baseUrl$paramString/drivers.json$queryString")
            response.data
        } catch (e: Exception) {
            return null
        }
    }
}