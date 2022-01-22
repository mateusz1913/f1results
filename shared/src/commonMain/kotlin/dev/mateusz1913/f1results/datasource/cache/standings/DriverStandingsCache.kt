package dev.mateusz1913.f1results.datasource.cache.standings

import dev.mateusz1913.f1results.datasource.cache.*
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingType

interface DriverStandingsCache {
    fun getDriverStanding(
        driverId: String,
        season: String
    ): Triple<DriverStandingsCachedData, DriverType, List<GetDriverConstructorsStandingWithDriverIdAndSeason>>

    fun getDriverStandings(
        season: String,
        round: String
    ): List<Triple<DriverStandingsCachedData, DriverType, List<GetDriverConstructorsStandingWithDriverIdAndSeason>>>

    fun getLatestDriverStandings(): List<Triple<DriverStandingsCachedData, DriverType, List<GetDriverConstructorsStandingWithDriverIdAndSeason>>>

    fun insertDriverStanding(driverStanding: DriverStandingType, season: String, round: String)
}