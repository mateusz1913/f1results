package dev.mateusz1913.f1results.datasource.repository.standings

import dev.mateusz1913.f1results.datasource.cache.standings.DriverStandingsCache
import dev.mateusz1913.f1results.datasource.cache.standings.toDriverStandingType
import dev.mateusz1913.f1results.datasource.cache.standings.toDriverStandingsType
import dev.mateusz1913.f1results.datasource.data.standings.*
import dev.mateusz1913.f1results.datasource.remote.standings.StandingsApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class DriverStandingsRepository(
    private val standingsApi: StandingsApi,
    private val driverStandingsCache: DriverStandingsCache
) {
    suspend fun fetchDriverStandings(season: String, round: String): DriverStandingsType? {
        val driverStandings = standingsApi.getSpecificDriverStandings(season, round)
        if (driverStandings != null && driverStandings.isNotEmpty()) {
            driverStandings[0].driverStandings.map {
                driverStandingsCache.insertDriverStanding(
                    it,
                    driverStandings[0].season,
                    driverStandings[0].round!!
                )
            }
        }
        return driverStandings?.get(0)
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

    suspend fun fetchSeasonDriverStanding(driverId: String, season: String): DriverStandingType? {
        val standingData = fetchDriversStandingsList(season = season, driverId = driverId)
        val standingsLists = standingData?.standingsTable?.standingsLists?.get(0)
        val standing = standingsLists?.driverStandings?.get(0)
        val standingSeason = standingsLists?.season
        val standingRound = standingsLists?.round
        if (standing != null && standingSeason != null && standingRound != null) {
            driverStandingsCache.insertDriverStanding(standing, standingSeason, standingRound)
        }
        return standing
    }

    fun getCachedLatestDriverStandings(): DriverStandingsType? {
        return try {
            val currentTimestamp = now().toEpochMilliseconds()
            val cachedDriverStandings = driverStandingsCache.getLatestDriverStandings()
            val (firstDriverStanding) = cachedDriverStandings[0]
            if (currentTimestamp < firstDriverStanding.timestamp + TIMESTAMP_THRESHOLD) {
                cachedDriverStandings.toDriverStandingsType()
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached latest driver standings", tag = "DriverStandingsRepository")
            null
        }
    }

    fun getCachedDriverStandings(season: String, round: String): DriverStandingsType? {
        return try {
            val currentTimestamp = now().toEpochMilliseconds()
            val cachedDriverStandings = driverStandingsCache.getDriverStandings(season, round)
            val (firstDriverStanding) = cachedDriverStandings[0]
            if (currentTimestamp < firstDriverStanding.timestamp + TIMESTAMP_THRESHOLD) {
                cachedDriverStandings.toDriverStandingsType()
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached driver standings", tag = "DriverStandingsRepository")
            null
        }
    }

    fun getCachedSeasonDriverStanding(driverId: String, season: String): DriverStandingType? {
        return try {
            val cachedDriverStanding = driverStandingsCache.getDriverStanding(
                driverId,
                season
            )
            val (driverStanding) = cachedDriverStanding
            val currentTimestamp = now().toEpochMilliseconds()
            if (currentTimestamp < driverStanding.timestamp + TIMESTAMP_THRESHOLD) {
                cachedDriverStanding.toDriverStandingType()
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached season driver standing", tag = "DriverStandingsRepository")
            null
        }
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}