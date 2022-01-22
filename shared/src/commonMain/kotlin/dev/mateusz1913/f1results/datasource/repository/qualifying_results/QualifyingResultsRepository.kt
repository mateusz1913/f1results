package dev.mateusz1913.f1results.datasource.repository.qualifying_results

import dev.mateusz1913.f1results.datasource.cache.qualifying_results.QualifyingResultsCache
import dev.mateusz1913.f1results.datasource.cache.qualifying_results.toArrayQualifyingResultType
import dev.mateusz1913.f1results.datasource.cache.qualifying_results.toRaceWithQualifyingResultsType
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.data.circuit.LocationType
import dev.mateusz1913.f1results.datasource.data.qualifying_results.QualifyingResultsData
import dev.mateusz1913.f1results.datasource.data.qualifying_results.RaceWithQualifyingResultsType
import dev.mateusz1913.f1results.datasource.remote.qualifying_results.QualifyingResultsApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class QualifyingResultsRepository(
    private val qualifyingResultsApi: QualifyingResultsApi,
    private val qualifyingResultsCache: QualifyingResultsCache
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
            qualifyingResultsCache.insertQualifyingResults(qualifyingResult)
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
        return qualifyingResultsApi.getQualifyingResult(
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
    }

    fun getCachedLatestQualifyingResults(): RaceWithQualifyingResultsType? {
        return try {
            val currentTimestamp = now().toEpochMilliseconds()
            val cachedQualifyingResults = qualifyingResultsCache.getLatestQualifyingResults()
            val (_, qualifyingResults) = cachedQualifyingResults
            if (currentTimestamp < qualifyingResults[0].timestamp + TIMESTAMP_THRESHOLD) {
                cachedQualifyingResults.toRaceWithQualifyingResultsType()
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached latest qualifyingResults", tag = "QualifyingResultsRepository")
            null
        }
    }

    fun getCachedQualifyingResult(season: String, round: String): RaceWithQualifyingResultsType? {
        return try {
            val currentTimestamp = now().toEpochMilliseconds()
            val cachedQualifyingResults = qualifyingResultsCache.getQualifyingResultsWithSeasonAndRound(
                season,
                round
            )
            val (_, qualifyingResults) = cachedQualifyingResults
            if (currentTimestamp < qualifyingResults[0].timestamp + TIMESTAMP_THRESHOLD) {
                cachedQualifyingResults.toRaceWithQualifyingResultsType()
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached qualifyingResults", tag = "QualifyingResultsRepository")
            null
        }
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}