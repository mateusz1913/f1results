package dev.mateusz1913.f1results.datasource.cache.qualifying_results

import dev.mateusz1913.f1results.datasource.cache.race_schedule.RaceScheduleCachedData
import dev.mateusz1913.f1results.datasource.data.qualifying_results.RaceWithQualifyingResultsType

interface QualifyingResultsCache {
    fun getLatestQualifyingResults(): Pair<RaceScheduleCachedData, List<QualifyingResultsCachedData>>
    fun getQualifyingResultsWithSeasonAndRound(
        season: String,
        round: String
    ): Pair<RaceScheduleCachedData, List<QualifyingResultsCachedData>>

    fun insertQualifyingResults(qualifyingResults: RaceWithQualifyingResultsType): Boolean
}