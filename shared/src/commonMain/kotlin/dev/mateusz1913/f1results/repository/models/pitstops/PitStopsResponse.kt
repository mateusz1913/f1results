package dev.mateusz1913.f1results.repository.models.pitstops

import dev.mateusz1913.f1results.repository.models.circuit.CircuitType
import kotlinx.serialization.Serializable

@Serializable
data class PitStopType(
    val driverId: String,
    val stop: String,
    val lap: String,
    val time: String?,
    val duration: String?
)

@Serializable
data class RaceWithPitStopsType(
    val season: String,
    val round: String,
    val url: String,
    val raceName: String,
    val Circuit: CircuitType,
    val date: String,
    val time: String,
    val PitStops: Array<PitStopType>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RaceWithPitStopsType

        if (season != other.season) return false
        if (round != other.round) return false
        if (url != other.url) return false
        if (raceName != other.raceName) return false
        if (Circuit != other.Circuit) return false
        if (date != other.date) return false
        if (time != other.time) return false
        if (!PitStops.contentEquals(other.PitStops)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = season.hashCode()
        result = 31 * result + round.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + raceName.hashCode()
        result = 31 * result + Circuit.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + PitStops.contentHashCode()
        return result
    }
}

@Serializable
data class RacesWithPitStopsTableType(
    val Races: Array<RaceWithPitStopsType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RacesWithPitStopsTableType

        if (!Races.contentEquals(other.Races)) return false

        return true
    }

    override fun hashCode(): Int {
        return Races.contentHashCode()
    }
}

@Serializable
data class MRDataLapsType(
    val series: String?,
    val url: String?,
    val limit: Int?,
    val offset: Int?,
    val total: Int?,
    val RaceTable: RacesWithPitStopsTableType
)

@Serializable
data class PitStopsResponse(
    val MRData: MRDataLapsType
)
