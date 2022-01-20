package dev.mateusz1913.f1results.datasource.cache.race_schedule

import dev.mateusz1913.f1results.datasource.cache.GetLatestRace
import dev.mateusz1913.f1results.datasource.cache.GetRaceWithRaceId
import dev.mateusz1913.f1results.datasource.cache.GetRaceWithSeason
import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceType

interface RaceScheduleCache {
    fun getLastRaceSchedule(): GetLatestRace
    fun getRaceScheduleWithSeason(season: String): List<GetRaceWithSeason>
    fun getRaceScheduleWithSeasonAndRound(season: String, round: String): GetRaceWithRaceId
    fun insertRaceSchedule(raceSchedule: RaceType)
    fun insertRaceScheduleList(raceScheduleList: Array<RaceType>)
}
