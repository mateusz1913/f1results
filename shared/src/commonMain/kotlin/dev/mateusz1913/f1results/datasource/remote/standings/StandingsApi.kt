package dev.mateusz1913.f1results.datasource.remote.standings

import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingsData
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingsType
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingsData
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingsType

interface StandingsApi {
    suspend fun getSpecificDriverStandings(
        season: String,
        round: String,
        position: Int?
    ): Array<DriverStandingsType>?

    suspend fun getSpecificConstructorStandings(
        season: String,
        round: String,
        position: Int?
    ): Array<ConstructorStandingsType>?

    suspend fun getDriverStandings(
        limit: Int?,
        offset: Int?,
        season: String?,
        driverId: String?,
        position: String?,
    ): DriverStandingsData?

    suspend fun getConstructorStandings(
        limit: Int?,
        offset: Int?,
        season: String?,
        constructorId: String?,
        position: String?,
    ): ConstructorStandingsData?
}