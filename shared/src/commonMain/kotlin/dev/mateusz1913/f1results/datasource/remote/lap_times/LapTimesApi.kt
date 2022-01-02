package dev.mateusz1913.f1results.datasource.remote.lap_times

import dev.mateusz1913.f1results.datasource.data.lap_times.RaceWithLapTimesType

interface LapTimesApi {
    suspend fun getSpecificLap(
        limit: Int?,
        offset: Int?,
        season: String,
        round: String,
        lap: Int,
        driverId: String?,
    ): RaceWithLapTimesType?
}