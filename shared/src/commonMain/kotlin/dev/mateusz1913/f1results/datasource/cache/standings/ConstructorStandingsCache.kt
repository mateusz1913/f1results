package dev.mateusz1913.f1results.datasource.cache.standings

import dev.mateusz1913.f1results.datasource.cache.GetConstructorStandingWithConstructorIdAndSeason
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingType

interface ConstructorStandingsCache {
    fun getConstructorStanding(
        constructorId: String,
        season: String
    ): GetConstructorStandingWithConstructorIdAndSeason

    fun insertConstructorStanding(constructorStanding: ConstructorStandingType, season: String)
}