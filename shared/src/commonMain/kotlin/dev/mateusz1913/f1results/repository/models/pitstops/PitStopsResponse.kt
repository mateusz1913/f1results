package dev.mateusz1913.f1results.repository.models.pitstops

import dev.mateusz1913.f1results.repository.models.base.BaseResponse
import dev.mateusz1913.f1results.repository.models.base.race.BaseRaceData
import dev.mateusz1913.f1results.repository.models.base.race.BaseRaceTableType
import dev.mateusz1913.f1results.repository.models.base.race.BaseRaceType
import dev.mateusz1913.f1results.repository.models.circuit.CircuitType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PitStopType(
    @SerialName("driverId")
    val driverId: String,
    @SerialName("stop")
    val stop: String,
    @SerialName("lap")
    val lap: String,
    @SerialName("time")
    val time: String?,
    @SerialName("duration")
    val duration: String?
)

@Serializable
data class RaceWithPitStopsType(
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
    @SerialName("PitStops")
    val pitStops: Array<PitStopType>,
): BaseRaceType {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RaceWithPitStopsType

        if (season != other.season) return false
        if (round != other.round) return false
        if (url != other.url) return false
        if (raceName != other.raceName) return false
        if (circuit != other.circuit) return false
        if (date != other.date) return false
        if (time != other.time) return false
        if (!pitStops.contentEquals(other.pitStops)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = season.hashCode()
        result = 31 * result + round.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + raceName.hashCode()
        result = 31 * result + circuit.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + pitStops.contentHashCode()
        return result
    }
}

@Serializable
data class RacesWithPitStopsTableType(
    @SerialName("Races")
    override val races: Array<RaceWithPitStopsType>
): BaseRaceTableType<RaceWithPitStopsType> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RacesWithPitStopsTableType

        if (!races.contentEquals(other.races)) return false

        return true
    }

    override fun hashCode(): Int {
        return races.contentHashCode()
    }
}

@Serializable
data class PitStopsData(
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
    override val raceTable: RacesWithPitStopsTableType
): BaseRaceData<RaceWithPitStopsType>

@Serializable
data class PitStopsResponse(
    @SerialName("MRData")
    override val data: PitStopsData
): BaseResponse
