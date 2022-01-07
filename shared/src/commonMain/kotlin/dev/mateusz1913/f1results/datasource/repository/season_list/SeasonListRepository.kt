package dev.mateusz1913.f1results.datasource.repository.season_list

import dev.mateusz1913.f1results.datasource.data.season_list.SeasonListData
import dev.mateusz1913.f1results.datasource.remote.season_list.SeasonListApi

class SeasonListRepository(private val seasonListApi: SeasonListApi) {
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
}