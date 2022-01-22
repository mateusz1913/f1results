package dev.mateusz1913.f1results.datasource.cache.race_schedule

import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceType

interface RaceScheduleCache {
    fun getLastRaceSchedule(): RaceScheduleCachedData
    fun getRaceScheduleFromCurrentSeason(): List<RaceScheduleCachedData>
    fun getRaceScheduleWithSeason(season: String): List<RaceScheduleCachedData>
    fun getRaceScheduleWithSeasonAndRound(season: String, round: String): RaceScheduleCachedData
    fun insertRaceSchedule(raceSchedule: RaceType)
    fun insertRaceScheduleList(raceScheduleList: Array<RaceType>)
}
