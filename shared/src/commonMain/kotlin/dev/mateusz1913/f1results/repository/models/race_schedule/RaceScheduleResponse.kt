package dev.mateusz1913.f1results.repository.models.race_schedule

import dev.mateusz1913.f1results.repository.models.circuit.CircuitType
import kotlinx.serialization.Serializable

@Serializable
data class RaceType(
    val season: String,
    val round: String,
    val url: String,
    val raceName: String,
    val Circuit: CircuitType,
    val date: String,
    val time: String,
)

@Serializable
data class RaceScheduleTableType(
    val Races: Array<RaceType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RaceScheduleTableType

        if (!Races.contentEquals(other.Races)) return false

        return true
    }

    override fun hashCode(): Int {
        return Races.contentHashCode()
    }
}

@Serializable
data class MRDataRaceScheduleType(
    val series: String?,
    val url: String?,
    val limit: Int?,
    val offset: Int?,
    val total: Int?,
    val RaceTable: RaceScheduleTableType
)

@Serializable
data class RaceScheduleResponse(
    val MRData: MRDataRaceScheduleType
)
