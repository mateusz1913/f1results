package dev.mateusz1913.f1results.repository.models.lap_times

import dev.mateusz1913.f1results.repository.models.circuit.CircuitType
import kotlinx.serialization.Serializable

@Serializable
data class TimingType(
    val driverId: String,
    val position: String,
    val time: String,
)

@Serializable
data class LapType(
    val number: String,
    val Timings: Array<TimingType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as LapType

        if (number != other.number) return false
        if (!Timings.contentEquals(other.Timings)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = number.hashCode()
        result = 31 * result + Timings.contentHashCode()
        return result
    }
}

@Serializable
data class RaceWithLapsType(
    val season: String,
    val round: String,
    val url: String,
    val raceName: String,
    val Circuit: CircuitType,
    val date: String,
    val time: String,
    val Laps: Array<LapType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RaceWithLapsType

        if (season != other.season) return false
        if (round != other.round) return false
        if (url != other.url) return false
        if (raceName != other.raceName) return false
        if (Circuit != other.Circuit) return false
        if (date != other.date) return false
        if (time != other.time) return false
        if (!Laps.contentEquals(other.Laps)) return false

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
        result = 31 * result + Laps.contentHashCode()
        return result
    }
}

@Serializable
data class RacesWithLapsTableType(
    val Races: Array<RaceWithLapsType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RacesWithLapsTableType

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
    val RaceTable: RacesWithLapsTableType
)

@Serializable
data class LapsResponse(
    val MRData: MRDataLapsType
)
