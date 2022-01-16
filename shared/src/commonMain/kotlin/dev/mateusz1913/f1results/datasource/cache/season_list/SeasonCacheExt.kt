package dev.mateusz1913.f1results.datasource.cache.season_list

import dev.mateusz1913.f1results.datasource.cache.GetSeasonsWithConstructorId
import dev.mateusz1913.f1results.datasource.cache.GetSeasonsWithDriverId
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonType

fun List<GetSeasonsWithConstructorId>.toArraySeasonType(): Array<SeasonType> {
    return map { SeasonType(it.season, it.url) }.toTypedArray()
}

fun List<GetSeasonsWithDriverId>.toArraySeasonType(): Array<SeasonType> {
    return map { SeasonType(it.season, it.url) }.toTypedArray()
}
