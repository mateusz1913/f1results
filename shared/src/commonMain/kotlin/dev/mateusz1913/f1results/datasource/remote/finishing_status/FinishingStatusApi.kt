package dev.mateusz1913.f1results.datasource.remote.finishing_status

import dev.mateusz1913.f1results.datasource.data.finishing_status.FinishingStatusData

interface FinishingStatusApi {
    suspend fun getFinishingStatusList(
        limit: Int?,
        offset: Int?,
        season: String?,
        round: String?,
        circuitId: String?,
        constructorId: String?,
        driverId: String?,
        grid: Int?,
        results: Int?,
        fastest: Int?,
        statusId: String?,
    ): FinishingStatusData?
}