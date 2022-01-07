package dev.mateusz1913.f1results.datasource.repository.race_schedule

import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceScheduleData
import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceType
import dev.mateusz1913.f1results.datasource.remote.race_schedule.RaceScheduleApi

class RaceScheduleRepository(private val raceScheduleApi: RaceScheduleApi) {
    suspend fun fetchRaceSchedule(
        season: String,
        round: String,
    ): RaceType? {
        return raceScheduleApi.getSpecificRaceSchedule(season, round)
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
}