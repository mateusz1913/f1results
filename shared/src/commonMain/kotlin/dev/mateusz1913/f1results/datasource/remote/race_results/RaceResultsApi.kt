package dev.mateusz1913.f1results.datasource.remote.race_results

import dev.mateusz1913.f1results.datasource.data.race_results.RaceResultsData
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType

interface RaceResultsApi {
    suspend fun getSpecificRaceResult(
        season: String,
        round: String,
        position: Int?
    ): RaceWithResultsType?

    suspend fun getRaceResults(
        limit: Int?,
        offset: Int?,
        season: String?,
        circuitId: String?,
        constructorId: String?,
        driverId: String?,
        grid: Int?,
        fastest: Int?,
        statusId: String?,
        position: Int?
    ): RaceResultsData?
}