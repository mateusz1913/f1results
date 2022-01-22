package dev.mateusz1913.f1results.datasource.cache.standings

import dev.mateusz1913.f1results.datasource.cache.GetConstructorStandingWithConstructorIdAndSeason
import dev.mateusz1913.f1results.datasource.cache.GetConstructorStandingsWithSeasonAndRound
import dev.mateusz1913.f1results.datasource.cache.GetLatestConstructorStandings
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingType

interface ConstructorStandingsCache {
    fun getConstructorStanding(
        constructorId: String,
        season: String
    ): GetConstructorStandingWithConstructorIdAndSeason

    fun getConstructorStandings(
        season: String,
        round: String
    ): List<GetConstructorStandingsWithSeasonAndRound>

    fun getLatestConstructorStandings(): List<GetLatestConstructorStandings>

    fun insertConstructorStanding(
        constructorStanding: ConstructorStandingType,
        season: String,
        round: String
    ): Boolean
}