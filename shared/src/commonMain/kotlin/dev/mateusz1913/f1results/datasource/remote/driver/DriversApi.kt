package dev.mateusz1913.f1results.datasource.remote.driver

import dev.mateusz1913.f1results.datasource.data.driver.DriverInfoData
import dev.mateusz1913.f1results.datasource.data.driver.DriverType

interface DriversApi {
    suspend fun getSpecificDriver(driverId: String): DriverType?

    suspend fun getDrivers(
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
    ): DriverInfoData?
}