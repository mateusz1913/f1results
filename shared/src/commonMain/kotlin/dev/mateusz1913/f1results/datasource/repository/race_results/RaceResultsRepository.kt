package dev.mateusz1913.f1results.datasource.repository.race_results

import dev.mateusz1913.f1results.datasource.cache.race_results.RaceResultsCache
import dev.mateusz1913.f1results.datasource.cache.race_results.toArrayResultType
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.data.circuit.LocationType
import dev.mateusz1913.f1results.datasource.data.race_results.RaceResultsData
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType
import dev.mateusz1913.f1results.datasource.remote.race_results.RaceResultsApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds

class RaceResultsRepository(private val raceResultsApi: RaceResultsApi, private val raceResultsCache: RaceResultsCache) {
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

    private fun getCachedLatestRaceResult(currentTimestamp: Double): RaceWithResultsType? {
        val (raceSchedule, raceResults) = raceResultsCache.getLatestRaceResults()
        if (currentTimestamp < raceResults[0].timestamp + TIMESTAMP_THRESHOLD) {
            return RaceWithResultsType(
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
                results = raceResults.toArrayResultType()
            )
        }
        return null
    }

    private fun getCachedRaceResultWithSeasonAndRound(
        season: String,
        round: String,
        currentTimestamp: Double
    ): RaceWithResultsType? {
        val (raceSchedule, raceResults) = raceResultsCache.getRaceResultsWithSeasonAndRound(season, round)
        if (currentTimestamp < raceResults[0].timestamp + TIMESTAMP_THRESHOLD) {
            return RaceWithResultsType(
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
                results = raceResults.toArrayResultType()
            )
        }
        return null
    }

    fun getCachedRaceResult(season: String, round: String): RaceWithResultsType? {
        return try {
            val currentTimestamp = now().toEpochMilliseconds()
            if (season == "current" && round == "last") {
                getCachedLatestRaceResult(currentTimestamp)
            } else {
                getCachedRaceResultWithSeasonAndRound(season, round, currentTimestamp)
            }
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}