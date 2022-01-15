package dev.mateusz1913.f1results.datasource.repository.driver

import dev.mateusz1913.f1results.datasource.cache.driver.DriverCache
import dev.mateusz1913.f1results.datasource.cache.driver.toDriverType
import dev.mateusz1913.f1results.datasource.data.driver.DriverInfoData
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import dev.mateusz1913.f1results.datasource.remote.driver.DriversApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class DriverRepository(private val driversApi: DriversApi, private val driverCache: DriverCache) {
    suspend fun fetchDriver(driverId: String): DriverType? {
        val driver = driversApi.getSpecificDriver(driverId)
        if (driver != null) {
            driverCache.insert(driver)
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
        return driversApi.getDrivers(
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
    }

    fun getCachedDriver(driverId: String): DriverType? {
        val cachedDriver = try {
            val cached = driverCache.get(driverId)
            val currentTimestamp = now().toEpochMilliseconds()
            if (currentTimestamp < cached.timestamp + TIMESTAMP_THRESHOLD) {
                cached
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached driver", tag = "DriverRepository")
            null
        }
        return cachedDriver?.toDriverType()
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}