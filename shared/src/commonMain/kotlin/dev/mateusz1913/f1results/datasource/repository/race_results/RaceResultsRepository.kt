package dev.mateusz1913.f1results.datasource.repository.race_results

import dev.mateusz1913.f1results.datasource.cache.race_results.RaceResultsCache
import dev.mateusz1913.f1results.datasource.cache.race_results.toArrayResultType
import dev.mateusz1913.f1results.datasource.cache.race_results.toRaceResultsCachedData
import dev.mateusz1913.f1results.datasource.cache.race_results.toRaceWithResultsType
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.data.circuit.LocationType
import dev.mateusz1913.f1results.datasource.data.race_results.RaceResultsData
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType
import dev.mateusz1913.f1results.datasource.remote.race_results.RaceResultsApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class RaceResultsRepository(
    private val raceResultsApi: RaceResultsApi,
    private val raceResultsCache: RaceResultsCache
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
            raceResultsCache.insertRaceResults(raceResults)
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
        return raceResultsApi.getRaceResults(
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
    }

    fun getCachedLatestRaceResult(): RaceWithResultsType? {
        return try {
            val currentTimestamp = now().toEpochMilliseconds()
            val cachedRaceResults = raceResultsCache.getLatestRaceResults()
            val (_, raceResults) = cachedRaceResults
            if (currentTimestamp < raceResults[0].timestamp + TIMESTAMP_THRESHOLD) {
                cachedRaceResults.toRaceWithResultsType()
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached latest race results", tag = "RaceResultsRepository")
            null
        }
    }

    fun getCachedRaceResult(season: String, round: String): RaceWithResultsType? {
        return try {
            val currentTimestamp = now().toEpochMilliseconds()
            val cachedRaceResults = raceResultsCache.getRaceResultsWithSeasonAndRound(
                season,
                round
            )
            val (_, raceResults) = cachedRaceResults
            if (currentTimestamp < raceResults[0].timestamp + TIMESTAMP_THRESHOLD) {
                cachedRaceResults.toRaceWithResultsType()
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached race results", tag = "RaceResultsRepository")
            null
        }
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}