package dev.mateusz1913.f1results.datasource.cache.season_list

import dev.mateusz1913.f1results.datasource.cache.GetSeasonsWithDriverId
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonType

interface SeasonCache {
    fun getSeasonsWithDriverId(driverId: String): List<GetSeasonsWithDriverId>
    fun insertDriverSeason(seasons: Array<SeasonType>, driverId: String)
}