package dev.mateusz1913.f1results.datasource.cache.race_results

import dev.mateusz1913.f1results.datasource.cache.race_schedule.RaceScheduleCachedData
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType

interface RaceResultsCache {
    fun getConstructorSeasonResults(
        season: String,
        constructorId: String
    ): Array<RaceWithResultsType>

//    fun getDriverSeasonResults(
//        season: String,
//        driverId: String
//    ): Pair<RaceScheduleCachedData, List<RaceResultsCachedData>>

    fun getLatestRaceResults(): Pair<RaceScheduleCachedData, List<RaceResultsCachedData>>
    fun getRaceResultsWithSeasonAndRound(
        season: String,
        round: String
    ): Pair<RaceScheduleCachedData, List<RaceResultsCachedData>>

    fun insertRaceResults(raceResults: RaceWithResultsType): Boolean
}
