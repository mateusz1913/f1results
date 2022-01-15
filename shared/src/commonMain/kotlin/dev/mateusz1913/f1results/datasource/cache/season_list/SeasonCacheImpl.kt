package dev.mateusz1913.f1results.datasource.cache.season_list

import dev.mateusz1913.f1results.datasource.cache.GetSeasonsWithDriverId
import dev.mateusz1913.f1results.datasource.cache.SeasonQueries
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonType
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds

class SeasonCacheImpl(private val queries: SeasonQueries): SeasonCache {
    override fun getSeasonsWithDriverId(driverId: String): List<GetSeasonsWithDriverId> {
        return queries.getSeasonsWithDriverId(driverId).executeAsList()
    }

    override fun insertDriverSeason(seasons: Array<SeasonType>, driverId: String) {
        seasons.forEach { season ->
            queries.insertDriverSeason(
                season = season.season,
                url = season.url,
                driver_id = driverId,
                timestamp = now().toEpochMilliseconds()
            )
        }
    }
}