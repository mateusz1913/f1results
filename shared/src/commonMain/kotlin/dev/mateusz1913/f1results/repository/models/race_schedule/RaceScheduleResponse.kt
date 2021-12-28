package dev.mateusz1913.f1results.repository.models.race_schedule

import dev.mateusz1913.f1results.repository.models.base.BaseResponse
import dev.mateusz1913.f1results.repository.models.base.race.BaseRaceData
import dev.mateusz1913.f1results.repository.models.base.race.BaseRaceTableType
import dev.mateusz1913.f1results.repository.models.base.race.BaseRaceType
import dev.mateusz1913.f1results.repository.models.circuit.CircuitType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RaceType(
    @SerialName("season")
    override val season: String,
    @SerialName("round")
    override val round: String,
    @SerialName("url")
    override val url: String,
    @SerialName("raceName")
    override val raceName: String,
    @SerialName("Circuit")
    override val circuit: CircuitType,
    @SerialName("date")
    override val date: String,
    @SerialName("time")
    override val time: String,
): BaseRaceType

@Serializable
data class RaceScheduleTableType(
    @SerialName("Races")
    override val races: Array<RaceType>
): BaseRaceTableType<RaceType> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RaceScheduleTableType

        if (!races.contentEquals(other.races)) return false

        return true
    }

    override fun hashCode(): Int {
        return races.contentHashCode()
    }
}

@Serializable
data class RaceScheduleData(
    @SerialName("series")
    override val series: String?,
    @SerialName("url")
    override val url: String?,
    @SerialName("limit")
    override val limit: Int?,
    @SerialName("offset")
    override val offset: Int?,
    @SerialName("total")
    override val total: Int?,
    @SerialName("RaceTable")
    override val raceTable: RaceScheduleTableType
): BaseRaceData<RaceType>

@Serializable
data class RaceScheduleResponse(
    override val data: RaceScheduleData
): BaseResponse
