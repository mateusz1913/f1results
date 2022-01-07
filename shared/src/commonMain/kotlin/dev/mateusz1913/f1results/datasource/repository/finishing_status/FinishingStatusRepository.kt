package dev.mateusz1913.f1results.datasource.repository.finishing_status

import dev.mateusz1913.f1results.datasource.data.finishing_status.FinishingStatusData
import dev.mateusz1913.f1results.datasource.remote.finishing_status.FinishingStatusApi

class FinishingStatusRepository(private val finishingStatusApi: FinishingStatusApi) {
    suspend fun fetchFinishingStatusList(
        limit: Int? = null,
        offset: Int? = null,
        season: String? = null,
        round: String? = null,
        circuitId: String? = null,
        constructorId: String? = null,
        driverId: String? = null,
        grid: Int? = null,
        results: Int? = null,
        fastest: Int? = null,
        statusId: String? = null,
    ): FinishingStatusData? {
        return finishingStatusApi.getFinishingStatusList(
            limit,
            offset,
            season,
            round,
            circuitId,
            constructorId,
            driverId,
            grid,
            results,
            fastest,
            statusId
        )
    }
}