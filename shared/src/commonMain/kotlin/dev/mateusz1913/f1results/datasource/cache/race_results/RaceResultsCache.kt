package dev.mateusz1913.f1results.datasource.cache.race_results

import dev.mateusz1913.f1results.datasource.cache.GetLastRaceResults
import dev.mateusz1913.f1results.datasource.cache.GetLatestRace
import dev.mateusz1913.f1results.datasource.cache.GetRaceResultsWithRaceId
import dev.mateusz1913.f1results.datasource.cache.GetRaceWithRaceId
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType

interface RaceResultsCache {
    fun getLatestRaceResults(): Pair<GetLatestRace, List<GetLastRaceResults>>
    fun getRaceResultsWithSeasonAndRound(
        season: String,
        round: String
    ): Pair<GetRaceWithRaceId, List<GetRaceResultsWithRaceId>>

    fun insertRaceResults(raceResults: RaceWithResultsType)
}
