package dev.mateusz1913.f1results.datasource.repository.race_schedule

import dev.mateusz1913.f1results.datasource.cache.race_schedule.RaceScheduleCache
import dev.mateusz1913.f1results.datasource.cache.race_schedule.toArrayRaceType
import dev.mateusz1913.f1results.datasource.cache.race_schedule.toRaceType
import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceScheduleData
import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceType
import dev.mateusz1913.f1results.datasource.remote.race_schedule.RaceScheduleApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class RaceScheduleRepository(
    private val raceScheduleApi: RaceScheduleApi,
    private val raceScheduleCache: RaceScheduleCache
) {
    suspend fun fetchRaceSchedule(
        season: String,
        round: String,
    ): RaceType? {
        val raceSchedule = raceScheduleApi.getSpecificRaceSchedule(season, round)
        if (raceSchedule != null) {
            raceScheduleCache.insertRaceSchedule(raceSchedule)
        }
        return raceSchedule
    }

    suspend fun fetchRaceScheduleListWithSeason(season: String): Array<RaceType>? {
        val raceScheduleList = raceScheduleApi.getSpecificRaceSchedule(season)
        if (raceScheduleList != null) {
            raceScheduleCache.insertRaceScheduleList(raceScheduleList)
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
        return raceScheduleApi.getRaceSchedules(
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
    }

    fun getCachedLatestRaceSchedule(): RaceType? {
        return try {
            val currentTimestamp = now().toEpochMilliseconds()
            val cached = raceScheduleCache.getLastRaceSchedule()
            if (currentTimestamp < cached.timestamp + TIMESTAMP_THRESHOLD) {
                cached.toRaceType()
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached latest race schedule", tag = "RaceScheduleRepository")
            null
        }
    }

    fun getCachedRaceSchedule(season: String, round: String): RaceType? {
        return try {
            val currentTimestamp = now().toEpochMilliseconds()
            val cached = raceScheduleCache.getRaceScheduleWithSeasonAndRound(season, round)
            if (currentTimestamp < cached.timestamp + TIMESTAMP_THRESHOLD) {
                cached.toRaceType()
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached race schedule", tag = "RaceScheduleRepository")
            null
        }
    }

    fun getCachedRaceScheduleListFromCurrentSeason(): Array<RaceType>? {
        return try {
            val cached = raceScheduleCache.getRaceScheduleFromCurrentSeason()
            val currentTimestamp = now().toEpochMilliseconds()
            if (currentTimestamp < cached[0].timestamp + TIMESTAMP_THRESHOLD) {
                cached.toArrayRaceType()
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached current season race schedule", tag = "RaceScheduleRepository")
            null
        }
    }

    fun getCachedRaceScheduleListWithSeason(season: String): Array<RaceType>? {
        return try {
            val cached = raceScheduleCache.getRaceScheduleWithSeason(season)
            val currentTimestamp = now().toEpochMilliseconds()
            if (currentTimestamp < cached[0].timestamp + TIMESTAMP_THRESHOLD) {
                cached.toArrayRaceType()
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached season race schedule", tag = "RaceScheduleRepository")
            null
        }
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}