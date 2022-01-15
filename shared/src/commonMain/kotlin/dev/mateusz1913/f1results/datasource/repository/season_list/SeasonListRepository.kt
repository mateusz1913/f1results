package dev.mateusz1913.f1results.datasource.repository.season_list

import dev.mateusz1913.f1results.datasource.cache.season_list.SeasonCache
import dev.mateusz1913.f1results.datasource.cache.season_list.toArraySeasonType
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonListData
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonType
import dev.mateusz1913.f1results.datasource.remote.season_list.SeasonListApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class SeasonListRepository(private val seasonListApi: SeasonListApi, private val seasonCache: SeasonCache) {
    suspend fun fetchDriverSeasonList(driverId: String): Array<SeasonType>? {
        val driverSeasonList = fetchSeasonList(driverId = driverId)
        if (driverSeasonList != null) {
            seasonCache.insertDriverSeason(driverSeasonList.seasonTable.seasons, driverId)
        }
        return driverSeasonList?.seasonTable?.seasons
    }

    suspend fun fetchSeasonList(
        limit: Int? = null,
        offset: Int? = null,
        circuitId: String? = null,
        constructorId: String? = null,
        constructorStandings: Int? = null,
        driverId: String? = null,
        driverStandings: Int? = null,
        grid: Int? = null,
        fastest: Int? = null,
        results: Int? = null,
        statusId: String? = null,
        order: String? = null,
    ): SeasonListData? {
        return seasonListApi.getSeasonList(
            limit,
            offset,
            circuitId,
            constructorId,
            constructorStandings,
            driverId,
            driverStandings,
            grid,
            fastest,
            results,
            statusId,
            order
        )
    }

    fun getCachedDriverSeasonList(driverId: String): Array<SeasonType>? {
        val cachedDriverSeasonList = try {
            val cached = seasonCache.getSeasonsWithDriverId(driverId)
            val currentTimestamp = now().toEpochMilliseconds()
            if (currentTimestamp < cached[0].timestamp + TIMESTAMP_THRESHOLD) {
                cached
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached driver season list", tag = "SeasonListRepository")
            null
        }
        return cachedDriverSeasonList?.toArraySeasonType()
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}