package dev.mateusz1913.f1results.datasource.remote.season_list

import dev.mateusz1913.f1results.datasource.data.season_list.SeasonListData

interface SeasonListApi {
    suspend fun getSeasonList(
        limit: Int?,
        offset: Int?,
        circuitId: String?,
        constructorId: String?,
        constructorStandings: Int?,
        driverId: String?,
        driverStandings: Int?,
        grid: Int?,
        fastest: Int?,
        results: Int?,
        statusId: String?,
        order: String?,
    ): SeasonListData?
}