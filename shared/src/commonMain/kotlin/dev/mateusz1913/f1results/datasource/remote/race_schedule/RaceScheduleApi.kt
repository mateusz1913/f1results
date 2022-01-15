package dev.mateusz1913.f1results.datasource.remote.race_schedule

import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceScheduleData
import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceType

interface RaceScheduleApi {
    suspend fun getSpecificRaceSchedule(
        season: String,
    ): Array<RaceType>?

    suspend fun getSpecificRaceSchedule(
        season: String,
        round: String,
    ): RaceType?

    suspend fun getRaceSchedules(
        limit: Int?,
        offset: Int?,
        season: String?,
        circuitId: String?,
        constructorId: String?,
        driverId: String?,
        grid: Int?,
        fastest: Int?,
        results: Int?,
        statusId: String?,
    ): RaceScheduleData?
}