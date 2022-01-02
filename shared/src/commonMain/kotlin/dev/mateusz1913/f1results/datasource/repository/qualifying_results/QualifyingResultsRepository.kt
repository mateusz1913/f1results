package dev.mateusz1913.f1results.datasource.repository.qualifying_results

import dev.mateusz1913.f1results.datasource.data.qualifying_results.QualifyingResultsData
import dev.mateusz1913.f1results.datasource.data.qualifying_results.RaceWithQualifyingResultsType
import dev.mateusz1913.f1results.datasource.remote.qualifying_results.QualifyingResultsApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class QualifyingResultsRepository: KoinComponent {
    private val qualifyingResultsApi: QualifyingResultsApi by inject()

    suspend fun fetchQualifyingResult(
        season: String,
        round: String,
        position: Int? = null
    ): RaceWithQualifyingResultsType? {
        return qualifyingResultsApi.getSpecificQualifyingResult(
            season,
            round,
            position
        )
    }

    suspend fun fetchQualifyingResultsList(
        limit: Int? = null,
        offset: Int? = null,
        season: String? = null,
        circuitId: String? = null,
        constructorId: String? = null,
        driverId: String? = null,
        grid: Int? = null,
        results: Int? = null,
        fastest: Int? = null,
        statusId: String? = null,
        position: Int? = null
    ): QualifyingResultsData? {
        return qualifyingResultsApi.getQualifyingResult(
            limit,
            offset,
            season,
            circuitId,
            constructorId,
            driverId,
            grid,
            results,
            fastest,
            statusId,
            position
        )
    }
}