package dev.mateusz1913.f1results.datasource.repository.qualifying_results

import dev.mateusz1913.f1results.datasource.cache.qualifying_results.QualifyingResultsCache
import dev.mateusz1913.f1results.datasource.cache.qualifying_results.toArrayQualifyingResultType
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.data.circuit.LocationType
import dev.mateusz1913.f1results.datasource.data.qualifying_results.QualifyingResultsData
import dev.mateusz1913.f1results.datasource.data.qualifying_results.RaceWithQualifyingResultsType
import dev.mateusz1913.f1results.datasource.remote.qualifying_results.QualifyingResultsApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds

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

    private fun getCachedLastQualifyingResults(currentTimestamp: Double): RaceWithQualifyingResultsType? {
        val (raceSchedule, qualifyingResults) = qualifyingResultsCache.getLatestQualifyingResults()
        if (currentTimestamp < qualifyingResults[0].timestamp + TIMESTAMP_THRESHOLD) {
            return RaceWithQualifyingResultsType(
                season = "${raceSchedule.season}",
                round = "${raceSchedule.round}",
                url = raceSchedule.url,
                raceName = raceSchedule.race_name,
                circuit = CircuitType(
                    circuitId = raceSchedule.circuit_id,
                    url = raceSchedule.circuit_url,
                    circuitName = raceSchedule.circuit_name,
                    location = LocationType(
                        alt = raceSchedule.circuit_alt,
                        lat = raceSchedule.circuit_lat,
                        long = raceSchedule.circuit_long,
                        locality = raceSchedule.circuit_locality,
                        country = raceSchedule.circuit_country
                    )
                ),
                date = raceSchedule.date,
                time = raceSchedule.time,
                qualifyingResults = qualifyingResults.toArrayQualifyingResultType()
            )
        }
        return null
    }

    private fun getCachedQualifyingResultWithSeasonAndRound(
        season: String,
        round: String,
        currentTimestamp: Double
    ): RaceWithQualifyingResultsType? {
        val (raceSchedule, qualifyingResults) = qualifyingResultsCache.getQualifyingResultsWithSeasonAndRound(
            season,
            round
        )
        if (currentTimestamp < qualifyingResults[0].timestamp + TIMESTAMP_THRESHOLD) {
            return RaceWithQualifyingResultsType(
                season = "${raceSchedule.season}",
                round = "${raceSchedule.round}",
                url = raceSchedule.url,
                raceName = raceSchedule.race_name,
                circuit = CircuitType(
                    circuitId = raceSchedule.circuit_id,
                    url = raceSchedule.circuit_url,
                    circuitName = raceSchedule.circuit_name,
                    location = LocationType(
                        alt = raceSchedule.circuit_alt,
                        lat = raceSchedule.circuit_lat,
                        long = raceSchedule.circuit_long,
                        locality = raceSchedule.circuit_locality,
                        country = raceSchedule.circuit_country
                    )
                ),
                date = raceSchedule.date,
                time = raceSchedule.time,
                qualifyingResults = qualifyingResults.toArrayQualifyingResultType()
            )
        }
        return null
    }

    fun getCachedQualifyingResult(season: String, round: String): RaceWithQualifyingResultsType? {
        return try {
            val currentTimestamp = now().toEpochMilliseconds()
            if (season == "current" && round == "last") {
                getCachedLastQualifyingResults(currentTimestamp)
            } else {
                getCachedQualifyingResultWithSeasonAndRound(season, round, currentTimestamp)
            }
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}