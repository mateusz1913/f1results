package dev.mateusz1913.f1results.datasource.remote.qualifying_results

import dev.mateusz1913.f1results.datasource.data.qualifying_results.QualifyingResultsData
import dev.mateusz1913.f1results.datasource.data.qualifying_results.RaceWithQualifyingResultsType

interface QualifyingResultsApi {
    suspend fun getSpecificQualifyingResult(
        season: String,
        round: String,
        position: Int?
    ): RaceWithQualifyingResultsType?

    suspend fun getQualifyingResult(
        limit: Int?,
        offset: Int?,
        season: String?,
        circuitId: String?,
        constructorId: String?,
        driverId: String?,
        grid: Int?,
        results: Int?,
        fastest: Int?,
        statusId: String?,
        position: Int?
    ): QualifyingResultsData?
}