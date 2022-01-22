package dev.mateusz1913.f1results.datasource.repository.race_results

import dev.mateusz1913.f1results.datasource.cache.race_results.RaceResultsCache
import dev.mateusz1913.f1results.datasource.cache.race_results.toRaceWithResultsType
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.*
import dev.mateusz1913.f1results.datasource.data.race_results.RaceResultsData
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType
import dev.mateusz1913.f1results.datasource.remote.race_results.RaceResultsApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class RaceResultsRepository(
    private val raceResultsApi: RaceResultsApi,
    private val raceResultsCache: RaceResultsCache,
    private val requestsTimestampsCache: RequestsTimestampsCache
) {
    suspend fun fetchRaceResult(
        season: String,
        round: String,
        position: Int? = null
    ): RaceWithResultsType? {
        val raceResults = raceResultsApi.getSpecificRaceResult(
            season,
            round,
            position
        )
        if (raceResults != null) {
            persistRaceResults(raceResults, season == "current" && round == "last")
        }
        return raceResults
    }

    suspend fun fetchRaceResultsList(
        limit: Int? = null,
        offset: Int? = null,
        season: String? = null,
        circuitId: String? = null,
        constructorId: String? = null,
        driverId: String? = null,
        grid: Int? = null,
        fastest: Int? = null,
        statusId: String? = null,
        position: Int? = null
    ): RaceResultsData? {
        val raceResultsData = raceResultsApi.getRaceResults(
            limit,
            offset,
            season,
            circuitId,
            constructorId,
            driverId,
            grid,
            fastest,
            statusId,
            position
        )
        raceResultsData?.raceTable?.races?.forEach {
            persistRaceResults(it)
        }
        return raceResultsData
    }

    fun getCachedLatestRaceResult(): RaceWithResultsType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(
                getRaceResultsRequest(
                    "current",
                    "last"
                )
            )?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cachedRaceResults = raceResultsCache.getLatestRaceResults()
            val (_, raceResults) = cachedRaceResults
            if (currentTimestamp > raceResults[0].timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cachedRaceResults.toRaceWithResultsType()
        } catch (e: Exception) {
            Napier.w("No cached latest race results ${e.message}", e, "RaceResultsRepository")
            return null
        }
    }

    fun getCachedRaceResult(season: String, round: String): RaceWithResultsType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(
                getRaceResultsRequest(
                    season,
                    round
                )
            )?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cachedRaceResults = raceResultsCache.getRaceResultsWithSeasonAndRound(
                season,
                round
            )
            val (_, raceResults) = cachedRaceResults
            if (currentTimestamp > raceResults[0].timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cachedRaceResults.toRaceWithResultsType()
        } catch (e: Exception) {
            Napier.w("No cached race results ${e.message}", e, "RaceResultsRepository")
            return null
        }
    }

    private fun persistRaceResults(
        raceResult: RaceWithResultsType,
        isLatestResult: Boolean = false
    ) {
        val succeeded = raceResultsCache.insertRaceResults(raceResult)
        if (succeeded) {
            val timestamp = now().toEpochMilliseconds()
            // If fetching latest results, let's also save timestamp of "latest results" request
            if (isLatestResult) {
                requestsTimestampsCache.insertRequestTimestamp(
                    getRaceResultsRequest("current", "last"), timestamp
                )
            }
            requestsTimestampsCache.insertRequestTimestamp(
                getRaceResultsRequest(
                    raceResult.season,
                    raceResult.round
                ), timestamp
            )
            requestsTimestampsCache.insertRequestTimestamp(
                getRaceScheduleRequest(
                    raceResult.season,
                    raceResult.season
                ), timestamp
            )
            requestsTimestampsCache.insertRequestTimestamp(
                getCircuitRequest(raceResult.circuit.circuitId), timestamp
            )
            raceResult.results.forEach {
                requestsTimestampsCache.insertRequestTimestamp(
                    getDriverRequest(it.driver.driverId),
                    timestamp
                )
                requestsTimestampsCache.insertRequestTimestamp(
                    getConstructorRequest(it.constructor.constructorId),
                    timestamp
                )
            }
        }
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}