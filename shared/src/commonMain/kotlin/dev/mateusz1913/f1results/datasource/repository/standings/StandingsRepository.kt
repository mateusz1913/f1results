package dev.mateusz1913.f1results.datasource.repository.standings

import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingsData
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingsType
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingsData
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingsType
import dev.mateusz1913.f1results.datasource.remote.standings.StandingsApi

class StandingsRepository(private val standingsApi: StandingsApi) {
    suspend fun fetchDriverStandings(
        season: String,
        round: String,
        position: Int? = null
    ): Array<DriverStandingsType>? {
        return standingsApi.getSpecificDriverStandings(
            season,
            round,
            position
        )
    }

    suspend fun fetchConstructorStandings(
        season: String,
        round: String,
        position: Int? = null
    ): Array<ConstructorStandingsType>? {
        return standingsApi.getSpecificConstructorStandings(
            season,
            round,
            position
        )
    }

    suspend fun fetchDriversStandingsList(
        limit: Int? = null,
        offset: Int? = null,
        season: String? = null,
        driverId: String? = null,
        position: String? = null,
    ): DriverStandingsData? {
        return standingsApi.getDriverStandings(
            limit,
            offset,
            season,
            driverId,
            position
        )
    }

    suspend fun fetchConstructorsStandingsList(
        limit: Int? = null,
        offset: Int? = null,
        season: String? = null,
        constructorId: String? = null,
        position: String? = null,
    ): ConstructorStandingsData? {
        return standingsApi.getConstructorStandings(
            limit,
            offset,
            season,
            constructorId,
            position
        )
    }
}