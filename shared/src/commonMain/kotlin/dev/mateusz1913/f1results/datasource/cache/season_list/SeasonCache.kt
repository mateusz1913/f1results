package dev.mateusz1913.f1results.datasource.cache.season_list

import dev.mateusz1913.f1results.datasource.cache.GetSeasonsWithConstructorId
import dev.mateusz1913.f1results.datasource.cache.GetSeasonsWithDriverId
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonType

interface SeasonCache {
    fun getSeasonsWithConstructorId(constructorId: String): List<GetSeasonsWithConstructorId>
    fun getSeasonsWithDriverId(driverId: String): List<GetSeasonsWithDriverId>
    fun insertConstructorSeason(seasons: Array<SeasonType>, constructorId: String): Boolean
    fun insertDriverSeason(seasons: Array<SeasonType>, driverId: String): Boolean
}