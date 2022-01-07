package dev.mateusz1913.f1results.datasource.repository.driver

import dev.mateusz1913.f1results.datasource.data.driver.DriverInfoData
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import dev.mateusz1913.f1results.datasource.remote.driver.DriversApi

class DriverRepository(private val driversApi: DriversApi){
    suspend fun fetchDriver(driverId: String): DriverType? {
        return driversApi.getSpecificDriver(driverId)
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
}