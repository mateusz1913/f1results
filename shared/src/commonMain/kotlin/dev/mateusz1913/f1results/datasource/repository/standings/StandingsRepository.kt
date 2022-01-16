package dev.mateusz1913.f1results.datasource.repository.standings

import dev.mateusz1913.f1results.datasource.cache.standings.ConstructorStandingsCache
import dev.mateusz1913.f1results.datasource.cache.standings.DriverStandingsCache
import dev.mateusz1913.f1results.datasource.cache.standings.toConstructorStandingType
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.datasource.data.standings.*
import dev.mateusz1913.f1results.datasource.remote.standings.StandingsApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class StandingsRepository(
    private val standingsApi: StandingsApi,
    private val driverStandingsCache: DriverStandingsCache,
    private val constructorStandingsCache: ConstructorStandingsCache
) {
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

    private suspend fun fetchDriversStandingsList(
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

    private suspend fun fetchConstructorsStandingsList(
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

    suspend fun fetchSeasonDriverStanding(driverId: String, season: String): DriverStandingType? {
        val standingData = fetchDriversStandingsList(season = season, driverId = driverId)
        val standing = standingData?.standingsTable?.standingsLists?.get(0)?.driverStandings?.get(0)
        if (standing != null) {
            driverStandingsCache.insertDriverStanding(standing, season)
        }
        return standing
    }

    suspend fun fetchSeasonConstructorStanding(constructorId: String, season: String): ConstructorStandingType? {
        val standingData = fetchConstructorsStandingsList(season = season, constructorId = constructorId)
        val standing = standingData?.standingsTable?.standingsLists?.get(0)?.constructorStandings?.get(0)
        if (standing != null) {
            constructorStandingsCache.insertConstructorStanding(standing, season)
        }
        return standing
    }

    fun getCachedSeasonDriverStanding(driverId: String, season: String): DriverStandingType? {
        return try {
            val (driverStanding, driver, driverConstructors) = driverStandingsCache.getDriverStanding(driverId, season)
            val cached = DriverStandingType(
                position = driverStanding.position,
                positionText = driverStanding.position_text,
                points = driverStanding.points,
                wins = driverStanding.wins,
                driver = driver,
                constructors = driverConstructors.map {
                    ConstructorType(
                        constructorId = it.constructor_id,
                        url = it.url,
                        name = it.name,
                        nationality = it.nationality
                    )
                }.toTypedArray()
            )
            val currentTimestamp = now().toEpochMilliseconds()
            if (currentTimestamp < driverStanding.timestamp + TIMESTAMP_THRESHOLD) {
                cached
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached driver standing", tag = "StandingsRepository")
            null
        }
    }

    fun getCachedSeasonConstructorStanding(constructorId: String, season: String): ConstructorStandingType? {
        val cachedStanding = try {
            val cached = constructorStandingsCache.getConstructorStanding(constructorId, season)
            val currentTimestamp = now().toEpochMilliseconds()
            if (currentTimestamp < cached.timestamp + TIMESTAMP_THRESHOLD) {
                cached
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached constructor standing", tag = "StandingRepository")
            null
        }
        return cachedStanding?.toConstructorStandingType()
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}