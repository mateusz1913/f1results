package dev.mateusz1913.f1results.repository.models.lap_times

import dev.mateusz1913.f1results.repository.models.base.BaseResponse
import dev.mateusz1913.f1results.repository.models.base.race.BaseRaceData
import dev.mateusz1913.f1results.repository.models.base.race.BaseRaceTableType
import dev.mateusz1913.f1results.repository.models.base.race.BaseRaceType
import dev.mateusz1913.f1results.repository.models.circuit.CircuitType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimingType(
    @SerialName("driverId")
    val driverId: String,
    @SerialName("position")
    val position: String,
    @SerialName("time")
    val time: String,
)

@Serializable
data class LapTimeType(
    @SerialName("number")
    val number: String,
    @SerialName("Timings")
    val timings: Array<TimingType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as LapTimeType

        if (number != other.number) return false
        if (!timings.contentEquals(other.timings)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = number.hashCode()
        result = 31 * result + timings.contentHashCode()
        return result
    }
}

@Serializable
data class RaceWithLapTimesType(
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
    @SerialName("Laps")
    val lapTimes: Array<LapTimeType>
): BaseRaceType {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RaceWithLapTimesType

        if (season != other.season) return false
        if (round != other.round) return false
        if (url != other.url) return false
        if (raceName != other.raceName) return false
        if (circuit != other.circuit) return false
        if (date != other.date) return false
        if (time != other.time) return false
        if (!lapTimes.contentEquals(other.lapTimes)) return false

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
        result = 31 * result + lapTimes.contentHashCode()
        return result
    }
}

@Serializable
data class RacesWithLapTimesTableType(
    @SerialName("Races")
    override val races: Array<RaceWithLapTimesType>
): BaseRaceTableType<RaceWithLapTimesType> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RacesWithLapTimesTableType

        if (!races.contentEquals(other.races)) return false

        return true
    }

    override fun hashCode(): Int {
        return races.contentHashCode()
    }
}

@Serializable
data class LapTimesData(
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
    override val raceTable: RacesWithLapTimesTableType
): BaseRaceData<RaceWithLapTimesType>

@Serializable
data class LapTimesResponse(
    @SerialName("MRData")
    override val data: LapTimesData
): BaseResponse
