package dev.mateusz1913.f1results.datasource.repository.driver

import dev.mateusz1913.f1results.datasource.cache.driver.DriverCache
import dev.mateusz1913.f1results.datasource.cache.driver.toDriverType
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.RequestsTimestampsCache
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.getDriverRequest
import dev.mateusz1913.f1results.datasource.data.driver.DriverInfoData
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import dev.mateusz1913.f1results.datasource.remote.driver.DriversApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class DriverRepository(
    private val driversApi: DriversApi,
    private val driverCache: DriverCache,
    private val requestsTimestampsCache: RequestsTimestampsCache
) {
    suspend fun fetchDriver(driverId: String): DriverType? {
        val driver = driversApi.getSpecificDriver(driverId)
        if (driver != null) {
            val succeeded = driverCache.insert(driver)
            if (succeeded) {
                requestsTimestampsCache.insertRequestTimestamp(
                    getDriverRequest(driverId),
                    now().toEpochMilliseconds()
                )
            }
        }
        return driver
    }

    suspend fun fetchDriversList(
        limit: Int? = null,
        offset: Int? = null,
        season: String? = null,
        round: String? = null,
        circuitId: String? = null,
        constructorId: String? = null,
        driverStandings: Int? = null,
        grid: Int? = null,
        results: Int? = null,
        fastest: Int? = null,
        statusId: String? = null,
    ): DriverInfoData? {
        val driverInfoData = driversApi.getDrivers(
            limit,
            offset,
            season,
            round,
            circuitId,
            constructorId,
            driverStandings,
            grid,
            results,
            fastest,
            statusId
        )
        if (driverInfoData != null) {
            val succeeded = driverCache.insert(driverInfoData.driverTable.drivers)
            if (succeeded) {
                driverInfoData.driverTable.drivers.forEach {
                    requestsTimestampsCache.insertRequestTimestamp(
                        getDriverRequest(it.driverId),
                        now().toEpochMilliseconds()
                    )
                }
            }
        }
        return driverInfoData
    }

    fun getCachedDriver(driverId: String): DriverType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(getDriverRequest(driverId))?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cached = driverCache.get(driverId)
            if (currentTimestamp > cached.timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cached.toDriverType()
        } catch (e: Exception) {
            Napier.w("No cached driver ${e.message}", e, "DriverRepository")
            return null
        }
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}