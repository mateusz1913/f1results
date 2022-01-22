package dev.mateusz1913.f1results.datasource.cache.season_list

import dev.mateusz1913.f1results.datasource.cache.GetSeasonsWithConstructorId
import dev.mateusz1913.f1results.datasource.cache.GetSeasonsWithDriverId
import dev.mateusz1913.f1results.datasource.cache.SeasonQueries
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonType
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class SeasonCacheImpl(private val queries: SeasonQueries): SeasonCache {
    override fun getSeasonsWithConstructorId(constructorId: String): List<GetSeasonsWithConstructorId> {
        return queries.getSeasonsWithConstructorId(constructorId).executeAsList()
    }

    override fun getSeasonsWithDriverId(driverId: String): List<GetSeasonsWithDriverId> {
        return queries.getSeasonsWithDriverId(driverId).executeAsList()
    }

    override fun insertConstructorSeason(seasons: Array<SeasonType>, constructorId: String): Boolean {
        return try {
            queries.transactionWithResult {
                seasons.forEach { season ->
                    queries.insertConstructorSeason(
                        id = "$${season.season}/$constructorId",
                        season = season.season.toLong(),
                        url = season.url,
                        constructor_id = constructorId,
                        timestamp = now().toEpochMilliseconds()
                    )
                }
                true
            }
        } catch (e: Exception) {
            Napier.w("insertConstructorSeason - ${e.message}", e, "SeasonCache")
            false
        }
    }

    override fun insertDriverSeason(seasons: Array<SeasonType>, driverId: String): Boolean {
        return try {
            queries.transactionWithResult {
                seasons.forEach { season ->
                    queries.insertDriverSeason(
                        id = "${season.season}/$driverId",
                        season = season.season.toLong(),
                        url = season.url,
                        driver_id = driverId,
                        timestamp = now().toEpochMilliseconds()
                    )
                }
                true
            }
        } catch (e: Exception) {
            Napier.w("insertDriverSeason - ${e.message}", e, "SeasonCache")
            false
        }
    }
}
