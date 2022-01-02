package dev.mateusz1913.f1results.datasource.repository.race_results

import dev.mateusz1913.f1results.datasource.data.race_results.RaceResultsData
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType
import dev.mateusz1913.f1results.datasource.remote.race_results.RaceResultsApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RaceResultsRepository: KoinComponent {
    private val raceResultsApi: RaceResultsApi by inject()

    suspend fun fetchRaceResult(
        season: String,
        round: String,
        position: Int? = null
    ): RaceWithResultsType? {
        return raceResultsApi.getSpecificRaceResult(
            season,
            round,
            position
        )
    }

    suspend fun fetchRaceResultsList(
        limit: Int? = null,
        offset: Int? = null,
        season: String? = null,
        circuitId: String? = null,
        constructorId: String? = null,
        driverId: String? = null,
        grid: Int? = null,
        fastest: Int? = null,
        statusId: String? = null,
        position: Int? = null
    ): RaceResultsData? {
        return raceResultsApi.getRaceResults(
            limit,
            offset,
            season,
            circuitId,
            constructorId,
            driverId,
            grid,
            fastest,
            statusId,
            position
        )
    }
}