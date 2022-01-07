package dev.mateusz1913.f1results.datasource.repository.pitstops

import dev.mateusz1913.f1results.datasource.data.pitstops.RaceWithPitStopsType
import dev.mateusz1913.f1results.datasource.remote.pitstops.PitStopsApi

class PitStopsRepository(private val pitStopsApi: PitStopsApi) {
    suspend fun fetchPitStops(
        limit: Int? = null,
        offset: Int? = null,
        season: String,
        round: String,
        pitStopCount: Int? = null,
        driverId: String? = null,
        lap: Int? = null,
    ): RaceWithPitStopsType? {
        return pitStopsApi.getPitStops(
            limit,
            offset,
            season,
            round,
            pitStopCount,
            driverId,
            lap
        )
    }
}