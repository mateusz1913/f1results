package dev.mateusz1913.f1results.datasource.repository.standings

import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.*
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
    private val driverStandingsCache: DriverStandingsCache,
    private val requestsTimestampsCache: RequestsTimestampsCache
) {
    suspend fun fetchDriverStandings(season: String, round: String): DriverStandingsType? {
        val driverStandings = standingsApi.getSpecificDriverStandings(season, round)
        if (driverStandings != null && driverStandings.isNotEmpty()) {
            driverStandings[0].driverStandings.map {
                persistDriverStandings(
                    it,
                    driverStandings[0].season,
                    driverStandings[0].round,
                    season == "current" && round == "last"
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
            persistDriverStandings(standing, standingSeason, standingRound)
        }
        return standing
    }

    fun getCachedLatestDriverStandings(): DriverStandingsType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(
                getDriverStandingsRequest("current", "last")
            )?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cachedDriverStandings = driverStandingsCache.getLatestDriverStandings()
            val (firstDriverStanding) = cachedDriverStandings[0]
            if (currentTimestamp > firstDriverStanding.timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cachedDriverStandings.toDriverStandingsType()
        } catch (e: Exception) {
            Napier.w(
                "No cached latest driver standings ${e.message}",
                e, "DriverStandingsRepository"
            )
            return null
        }
    }

    fun getCachedDriverStandings(season: String, round: String): DriverStandingsType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(
                getDriverStandingsRequest(season, round)
            )?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cachedDriverStandings = driverStandingsCache.getDriverStandings(season, round)
            val (firstDriverStanding) = cachedDriverStandings[0]
            if (currentTimestamp > firstDriverStanding.timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cachedDriverStandings.toDriverStandingsType()
        } catch (e: Exception) {
            Napier.w("No cached driver standings ${e.message}", e, "DriverStandingsRepository")
            return null
        }
    }

    fun getCachedSeasonDriverStanding(driverId: String, season: String): DriverStandingType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(
                getDriverSeasonStandingRequest(driverId, season)
            )?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cachedDriverStanding = driverStandingsCache.getDriverStanding(
                driverId,
                season
            )
            val (driverStanding) = cachedDriverStanding
            if (currentTimestamp < driverStanding.timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cachedDriverStanding.toDriverStandingType()
        } catch (e: Exception) {
            Napier.w(
                "No cached season driver standing ${e.message}",
                e, "DriverStandingsRepository"
            )
            return null
        }
    }

    private fun persistDriverStandings(
        driverStanding: DriverStandingType,
        season: String,
        round: String,
        isLatest: Boolean = false
    ) {
        val succeeded = driverStandingsCache.insertDriverStanding(driverStanding, season, round)
        if (succeeded) {
            val currentTimestamp = now().toEpochMilliseconds()
            if (isLatest) {
                requestsTimestampsCache.insertRequestTimestamp(
                    getDriverStandingsRequest("current", "last"),
                    currentTimestamp
                )
            }
            requestsTimestampsCache.insertRequestTimestamp(
                getDriverStandingsRequest(season, round),
                currentTimestamp
            )
            requestsTimestampsCache.insertRequestTimestamp(
                getDriverRequest(driverStanding.driver.driverId),
                currentTimestamp
            )
            driverStanding.constructors.forEach {
                requestsTimestampsCache.insertRequestTimestamp(
                    getConstructorRequest(it.constructorId),
                    currentTimestamp
                )
            }
        }
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}