package dev.mateusz1913.f1results.datasource.remote.pitstops

import dev.mateusz1913.f1results.datasource.data.pitstops.RaceWithPitStopsType

interface PitStopsApi {
    suspend fun getPitStops(
        limit: Int?,
        offset: Int?,
        season: String,
        round: String,
        pitStopCount: Int?,
        driverId: String?,
        lap: Int?,
    ): RaceWithPitStopsType?
}