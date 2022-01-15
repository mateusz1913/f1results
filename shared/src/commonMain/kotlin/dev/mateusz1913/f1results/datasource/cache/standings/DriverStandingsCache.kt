package dev.mateusz1913.f1results.datasource.cache.standings

import dev.mateusz1913.f1results.datasource.cache.GetDriverConstructorsStandingWithDriverIdAndSeason
import dev.mateusz1913.f1results.datasource.cache.GetDriverStandingWithDriverIdAndSeason
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingType

interface DriverStandingsCache {
    fun getDriverStanding(
        driverId: String,
        season: String
    ): Triple<GetDriverStandingWithDriverIdAndSeason, DriverType, List<GetDriverConstructorsStandingWithDriverIdAndSeason>>

    fun insertDriverStanding(driverStanding: DriverStandingType, driverId: String, season: String)
}