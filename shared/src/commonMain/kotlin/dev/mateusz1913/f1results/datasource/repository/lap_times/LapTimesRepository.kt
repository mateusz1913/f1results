package dev.mateusz1913.f1results.datasource.repository.lap_times

import dev.mateusz1913.f1results.datasource.data.lap_times.RaceWithLapTimesType
import dev.mateusz1913.f1results.datasource.remote.lap_times.LapTimesApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LapTimesRepository: KoinComponent {
    private val lapTimesApi: LapTimesApi by inject()

    suspend fun fetchLapTime(
        limit: Int? = null,
        offset: Int? = null,
        season: String,
        round: String,
        lap: Int,
        driverId: String? = null,
    ): RaceWithLapTimesType? {
        return lapTimesApi.getSpecificLap(
            limit,
            offset,
            season,
            round,
            lap,
            driverId
        )
    }
}