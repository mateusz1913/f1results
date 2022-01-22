package dev.mateusz1913.f1results.datasource.repository.standings

import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.RequestsTimestampsCache
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.getConstructorRequest
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.getConstructorSeasonStandingRequest
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.getConstructorStandingsRequest
import dev.mateusz1913.f1results.datasource.cache.standings.ConstructorStandingsCache
import dev.mateusz1913.f1results.datasource.cache.standings.toConstructorStandingType
import dev.mateusz1913.f1results.datasource.cache.standings.toConstructorStandingsCachedData
import dev.mateusz1913.f1results.datasource.cache.standings.toConstructorStandingsType
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingType
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingsData
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingsType
import dev.mateusz1913.f1results.datasource.remote.standings.StandingsApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class ConstructorStandingsRepository(
    private val standingsApi: StandingsApi,
    private val constructorStandingsCache: ConstructorStandingsCache,
    private val requestsTimestampsCache: RequestsTimestampsCache
) {
    suspend fun fetchConstructorStandings(
        season: String,
        round: String
    ): ConstructorStandingsType? {
        val constructorStandings = standingsApi.getSpecificConstructorStandings(
            season,
            round,
        )
        if (constructorStandings != null && constructorStandings.isNotEmpty()) {
            constructorStandings[0].constructorStandings.map {
                persistConstructorStandings(
                    it, constructorStandings[0].season,
                    constructorStandings[0].round,
                    season == "current" && round == "last"
                )
            }
        }
        return constructorStandings?.get(0)
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

    suspend fun fetchSeasonConstructorStanding(
        constructorId: String,
        season: String
    ): ConstructorStandingType? {
        val standingData =
            fetchConstructorsStandingsList(season = season, constructorId = constructorId)
        val standingLists =
            standingData?.standingsTable?.standingsLists?.get(0)
        val standing = standingLists?.constructorStandings?.get(0)
        val standingSeason = standingLists?.season
        val standingRound = standingLists?.round
        if (standing != null && standingSeason != null && standingRound != null) {
            persistConstructorStandings(standing, standingSeason, standingRound)
        }
        return standing
    }

    fun getCachedLatestConstructorStandings(): ConstructorStandingsType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(
                getConstructorStandingsRequest("current", "last")
            )?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cachedConstructorStandings =
                constructorStandingsCache.getLatestConstructorStandings()
            if (currentTimestamp > cachedConstructorStandings[0].timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cachedConstructorStandings.map { it.toConstructorStandingsCachedData() }
                .toConstructorStandingsType()
        } catch (e: Exception) {
            Napier.w(
                "No cached latest constructor standings ${e.message}",
                e, "ConstructorStandingsRepository"
            )
            return null
        }
    }

    fun getCachedConstructorStandings(season: String, round: String): ConstructorStandingsType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(
                getConstructorStandingsRequest(season, round)
            )?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cachedConstructorStandings =
                constructorStandingsCache.getConstructorStandings(season, round)
            if (currentTimestamp > cachedConstructorStandings[0].timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cachedConstructorStandings.map { it.toConstructorStandingsCachedData() }
                .toConstructorStandingsType()
        } catch (e: Exception) {
            Napier.w(
                "No cached constructor standings ${e.message}",
                e, "ConstructorStandingsRepository"
            )
            return null
        }
    }

    fun getCachedSeasonConstructorStanding(
        constructorId: String,
        season: String
    ): ConstructorStandingType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(
                getConstructorSeasonStandingRequest(constructorId, season)
            )?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cached = constructorStandingsCache.getConstructorStanding(constructorId, season)
            if (currentTimestamp > cached.timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cached.toConstructorStandingType()
        } catch (e: Exception) {
            Napier.w(
                "No cached season constructor standing ${e.message}",
                e, "ConstructorStandingsRepository"
            )
            return null
        }
    }

    private fun persistConstructorStandings(
        constructorStanding: ConstructorStandingType,
        season: String,
        round: String,
        isLatest: Boolean = false
    ) {
        val succeeded =
            constructorStandingsCache.insertConstructorStanding(constructorStanding, season, round)
        if (succeeded) {
            val currentTimestamp = now().toEpochMilliseconds()
            if (isLatest) {
                requestsTimestampsCache.insertRequestTimestamp(
                    getConstructorStandingsRequest("current", "last"),
                    currentTimestamp
                )
            }
            requestsTimestampsCache.insertRequestTimestamp(
                getConstructorStandingsRequest(season, round),
                currentTimestamp
            )
            requestsTimestampsCache.insertRequestTimestamp(
                getConstructorRequest(constructorStanding.constructor.constructorId),
                currentTimestamp
            )
        }
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}