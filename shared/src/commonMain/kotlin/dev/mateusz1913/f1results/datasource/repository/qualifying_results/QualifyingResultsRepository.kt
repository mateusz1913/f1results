package dev.mateusz1913.f1results.datasource.repository.qualifying_results

import dev.mateusz1913.f1results.datasource.cache.qualifying_results.QualifyingResultsCache
import dev.mateusz1913.f1results.datasource.cache.qualifying_results.toRaceWithQualifyingResultsType
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.*
import dev.mateusz1913.f1results.datasource.data.qualifying_results.QualifyingResultsData
import dev.mateusz1913.f1results.datasource.data.qualifying_results.RaceWithQualifyingResultsType
import dev.mateusz1913.f1results.datasource.remote.qualifying_results.QualifyingResultsApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class QualifyingResultsRepository(
    private val qualifyingResultsApi: QualifyingResultsApi,
    private val qualifyingResultsCache: QualifyingResultsCache,
    private val requestsTimestampsCache: RequestsTimestampsCache
) {
    suspend fun fetchQualifyingResult(
        season: String,
        round: String,
        position: Int? = null
    ): RaceWithQualifyingResultsType? {
        val qualifyingResult = qualifyingResultsApi.getSpecificQualifyingResult(
            season,
            round,
            position
        )
        if (qualifyingResult != null) {
            persistQualifyingResults(qualifyingResult, season == "current" && round == "last")
        }
        return qualifyingResult
    }

    suspend fun fetchQualifyingResultsList(
        limit: Int? = null,
        offset: Int? = null,
        season: String? = null,
        circuitId: String? = null,
        constructorId: String? = null,
        driverId: String? = null,
        grid: Int? = null,
        results: Int? = null,
        fastest: Int? = null,
        statusId: String? = null,
        position: Int? = null
    ): QualifyingResultsData? {
        val qualifyingResultsData = qualifyingResultsApi.getQualifyingResult(
            limit,
            offset,
            season,
            circuitId,
            constructorId,
            driverId,
            grid,
            results,
            fastest,
            statusId,
            position
        )
        qualifyingResultsData?.raceTable?.races?.forEach { qualifyingResult ->
            persistQualifyingResults(qualifyingResult)
        }
        return qualifyingResultsData
    }

    fun getCachedLatestQualifyingResults(): RaceWithQualifyingResultsType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(
                getQualifyingResultsRequest(
                    "current",
                    "last"
                )
            )?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cachedQualifyingResults = qualifyingResultsCache.getLatestQualifyingResults()
            val (_, qualifyingResults) = cachedQualifyingResults
            if (currentTimestamp > qualifyingResults[0].timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cachedQualifyingResults.toRaceWithQualifyingResultsType()
        } catch (e: Exception) {
            Napier.w(
                "No cached latest qualifyingResults ${e.message}",
                e, "QualifyingResultsRepository"
            )
            return null
        }
    }

    fun getCachedQualifyingResults(season: String, round: String): RaceWithQualifyingResultsType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(
                getQualifyingResultsRequest(
                    season,
                    round
                )
            )?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cachedQualifyingResults =
                qualifyingResultsCache.getQualifyingResultsWithSeasonAndRound(
                    season,
                    round
                )
            val (_, qualifyingResults) = cachedQualifyingResults
            if (currentTimestamp > qualifyingResults[0].timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cachedQualifyingResults.toRaceWithQualifyingResultsType()
        } catch (e: Exception) {
            Napier.w(
                "No cached qualifyingResults ${e.message}",
                e,"QualifyingResultsRepository"
            )
            return null
        }
    }

    private fun persistQualifyingResults(
        qualifyingResult: RaceWithQualifyingResultsType,
        isLatestResult: Boolean = false
    ) {
        val succeeded = qualifyingResultsCache.insertQualifyingResults(qualifyingResult)
        if (succeeded) {
            val timestamp = now().toEpochMilliseconds()
            // If fetching latest results, let's also save timestamp of "latest results" request
            if (isLatestResult) {
                requestsTimestampsCache.insertRequestTimestamp(
                    getQualifyingResultsRequest("current", "last"), timestamp
                )
            }
            requestsTimestampsCache.insertRequestTimestamp(
                getQualifyingResultsRequest(
                    qualifyingResult.season,
                    qualifyingResult.round
                ), timestamp
            )
            requestsTimestampsCache.insertRequestTimestamp(
                getRaceScheduleRequest(
                    qualifyingResult.season,
                    qualifyingResult.season
                ), timestamp
            )
            requestsTimestampsCache.insertRequestTimestamp(
                getCircuitRequest(qualifyingResult.circuit.circuitId), timestamp
            )
            qualifyingResult.qualifyingResults.forEach {
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