package dev.mateusz1913.f1results.datasource.cache.season_list

import dev.mateusz1913.f1results.datasource.cache.GetSeasonsWithConstructorId
import dev.mateusz1913.f1results.datasource.cache.GetSeasonsWithDriverId
import dev.mateusz1913.f1results.datasource.cache.SeasonQueries
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonType
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds

class SeasonCacheImpl(private val queries: SeasonQueries): SeasonCache {
    override fun getSeasonsWithConstructorId(constructorId: String): List<GetSeasonsWithConstructorId> {
        return queries.getSeasonsWithConstructorId(constructorId).executeAsList()
    }

    override fun getSeasonsWithDriverId(driverId: String): List<GetSeasonsWithDriverId> {
        return queries.getSeasonsWithDriverId(driverId).executeAsList()
    }

    override fun insertConstructorSeason(seasons: Array<SeasonType>, constructorId: String) {
        seasons.forEach { season ->
            queries.insertConstructorSeason(
                season = season.season,
                url = season.url,
                constructor_id = constructorId,
                timestamp = now().toEpochMilliseconds()
            )
        }
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
