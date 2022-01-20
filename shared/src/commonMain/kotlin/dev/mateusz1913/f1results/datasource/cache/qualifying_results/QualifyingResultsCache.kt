package dev.mateusz1913.f1results.datasource.cache.qualifying_results

import dev.mateusz1913.f1results.datasource.cache.GetLastQualifyingResults
import dev.mateusz1913.f1results.datasource.cache.GetLatestRace
import dev.mateusz1913.f1results.datasource.cache.GetQualifyingResultsWithRaceId
import dev.mateusz1913.f1results.datasource.cache.GetRaceWithRaceId
import dev.mateusz1913.f1results.datasource.data.qualifying_results.RaceWithQualifyingResultsType

interface QualifyingResultsCache {
    fun getLatestQualifyingResults(): Pair<GetLatestRace, List<GetLastQualifyingResults>>
    fun getQualifyingResultsWithSeasonAndRound(
        season: String,
        round: String
    ): Pair<GetRaceWithRaceId, List<GetQualifyingResultsWithRaceId>>

    fun insertQualifyingResults(qualifyingResults: RaceWithQualifyingResultsType)
}