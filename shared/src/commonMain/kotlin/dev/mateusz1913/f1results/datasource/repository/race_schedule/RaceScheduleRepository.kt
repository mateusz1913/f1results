package dev.mateusz1913.f1results.datasource.repository.race_schedule

import dev.mateusz1913.f1results.datasource.cache.race_schedule.RaceScheduleCache
import dev.mateusz1913.f1results.datasource.cache.race_schedule.toArrayRaceType
import dev.mateusz1913.f1results.datasource.cache.race_schedule.toRaceType
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.RequestsTimestampsCache
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.getCircuitRequest
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.getRaceScheduleListRequest
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.getRaceScheduleRequest
import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceScheduleData
import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceType
import dev.mateusz1913.f1results.datasource.remote.race_schedule.RaceScheduleApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class RaceScheduleRepository(
    private val raceScheduleApi: RaceScheduleApi,
    private val raceScheduleCache: RaceScheduleCache,
    private val requestsTimestampsCache: RequestsTimestampsCache
) {
    suspend fun fetchRaceSchedule(
        season: String,
        round: String,
    ): RaceType? {
        val raceSchedule = raceScheduleApi.getSpecificRaceSchedule(season, round)
        if (raceSchedule != null) {
            persistRaceSchedule(raceSchedule, season == "current" && round == "last")
        }
        return raceSchedule
    }

    suspend fun fetchRaceScheduleListWithSeason(season: String): Array<RaceType>? {
        val raceScheduleList = raceScheduleApi.getSpecificRaceSchedule(season)
        if (raceScheduleList != null) {
            persistRaceScheduleListWithSeason(raceScheduleList, season == "current")
        }
        return raceScheduleList
    }

    suspend fun fetchRaceScheduleList(
        limit: Int? = null,
        offset: Int? = null,
        season: String? = null,
        circuitId: String? = null,
        constructorId: String? = null,
        driverId: String? = null,
        grid: Int? = null,
        fastest: Int? = null,
        results: Int? = null,
        statusId: String? = null,
    ): RaceScheduleData? {
        val raceScheduleData = raceScheduleApi.getRaceSchedules(
            limit,
            offset,
            season,
            circuitId,
            constructorId,
            driverId,
            grid,
            fastest,
            results,
            statusId
        )
        raceScheduleData?.raceTable?.races?.forEach { raceSchedule ->
            persistRaceSchedule(raceSchedule)
        }
        return raceScheduleData
    }

    fun getCachedLatestRaceSchedule(): RaceType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(
                getRaceScheduleRequest(
                    "current",
                    "last"
                )
            )?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cached = raceScheduleCache.getLastRaceSchedule()
            if (currentTimestamp > cached.timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cached.toRaceType()
        } catch (e: Exception) {
            Napier.w("No cached latest race schedule ${e.message}", e, "RaceScheduleRepository")
            return null
        }
    }

    fun getCachedRaceSchedule(season: String, round: String): RaceType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(
                getRaceScheduleRequest(
                    season,
                    round
                )
            )?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cached = raceScheduleCache.getRaceScheduleWithSeasonAndRound(season, round)
            if (currentTimestamp > cached.timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cached.toRaceType()
        } catch (e: Exception) {
            Napier.w("No cached race schedule ${e.message}", e, "RaceScheduleRepository")
            return null
        }
    }

    fun getCachedRaceScheduleListFromCurrentSeason(): Array<RaceType>? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(
                getRaceScheduleListRequest(season = "current")
            )?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cached = raceScheduleCache.getRaceScheduleFromCurrentSeason()
            if (currentTimestamp > cached[0].timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cached.toArrayRaceType()
        } catch (e: Exception) {
            Napier.w(
                "No cached current season race schedule ${e.message}",
                e, "RaceScheduleRepository"
            )
            return null
        }
    }

    fun getCachedRaceScheduleListWithSeason(season: String): Array<RaceType>? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(getRaceScheduleListRequest(season))?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cached = raceScheduleCache.getRaceScheduleWithSeason(season)
            if (currentTimestamp > cached[0].timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cached.toArrayRaceType()
        } catch (e: Exception) {
            Napier.w("No cached season race schedule ${e.message}", e, "RaceScheduleRepository")
            return null
        }
    }

    private fun persistRaceSchedule(raceSchedule: RaceType, isLatest: Boolean = false) {
        val succeeded = raceScheduleCache.insertRaceSchedule(raceSchedule)
        if (succeeded) {
            val timestamp = now().toEpochMilliseconds()
            // If fetching latest schedule, let's also save timestamp of "latest schedule" request
            if (isLatest) {
                requestsTimestampsCache.insertRequestTimestamp(
                    getRaceScheduleRequest("current", "last"), timestamp
                )
            }
            requestsTimestampsCache.insertRequestTimestamp(
                getRaceScheduleRequest(
                    raceSchedule.season,
                    raceSchedule.season
                ), timestamp
            )
            requestsTimestampsCache.insertRequestTimestamp(
                getCircuitRequest(raceSchedule.circuit.circuitId), timestamp
            )
        }
    }

    private fun persistRaceScheduleListWithSeason(
        raceScheduleList: Array<RaceType>,
        isLatest: Boolean = false
    ) {
        val succeeded = raceScheduleCache.insertRaceScheduleList(raceScheduleList)
        if (succeeded && raceScheduleList.isNotEmpty()) {
            val timestamp = now().toEpochMilliseconds()
            // If fetching latest schedule list, let's also save timestamp of "latest schedule list" request
            if (isLatest) {
                requestsTimestampsCache.insertRequestTimestamp(
                    getRaceScheduleListRequest("current"), timestamp
                )
            }
            requestsTimestampsCache.insertRequestTimestamp(
                getRaceScheduleListRequest(raceScheduleList[0].season), timestamp
            )
            raceScheduleList.forEach {
                requestsTimestampsCache.insertRequestTimestamp(
                    getCircuitRequest(it.circuit.circuitId), timestamp
                )
            }
        }
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}