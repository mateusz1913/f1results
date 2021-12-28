package dev.mateusz1913.f1results.repository.models.race_results

import dev.mateusz1913.f1results.repository.models.base.BaseResponse
import dev.mateusz1913.f1results.repository.models.base.race.BaseRaceData
import dev.mateusz1913.f1results.repository.models.base.race.BaseRaceTableType
import dev.mateusz1913.f1results.repository.models.base.race.BaseRaceType
import dev.mateusz1913.f1results.repository.models.circuit.CircuitType
import dev.mateusz1913.f1results.repository.models.constructor.ConstructorType
import dev.mateusz1913.f1results.repository.models.driver.DriverType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DurationType(
    @SerialName("millis")
    val millis: String?,
    @SerialName("time")
    val time: String,
)

@Serializable
data class AverageSpeedType(
    @SerialName("speed")
    val speed: String,
    @SerialName("units")
    val units: String?
)

@Serializable
data class FastestLapType(
    @SerialName("rank")
    val rank: String,
    @SerialName("lap")
    val lap: String,
    @SerialName("time")
    val time: DurationType,
    @SerialName("AverageSpeed")
    val averageSpeed: AverageSpeedType
)

@Serializable
data class ResultType(
    @SerialName("number")
    val number: String?,
    @SerialName("position")
    val position: String,
    @SerialName("positionText")
    val positionText: String,
    @SerialName("points")
    val points: String?,
    @SerialName("Driver")
    val driver: DriverType,
    @SerialName("Constructor")
    val constructor: ConstructorType,
    @SerialName("grid")
    val grid: String,
    @SerialName("laps")
    val laps: String,
    @SerialName("status")
    val status: String,
    @SerialName("Time")
    val time: DurationType?,
    @SerialName("FastestLap")
    val fastestLap: FastestLapType?
)

@Serializable
data class RaceWithResultsType(
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
    @SerialName("Results")
    val results: Array<ResultType>
): BaseRaceType {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RaceWithResultsType

        if (season != other.season) return false
        if (round != other.round) return false
        if (url != other.url) return false
        if (raceName != other.raceName) return false
        if (circuit != other.circuit) return false
        if (date != other.date) return false
        if (time != other.time) return false
        if (!results.contentEquals(other.results)) return false

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
        result = 31 * result + results.contentHashCode()
        return result
    }
}

@Serializable
data class RacesWithResultsTableType(
    @SerialName("Races")
    override val races: Array<RaceWithResultsType>
): BaseRaceTableType<RaceWithResultsType> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RacesWithResultsTableType

        if (!races.contentEquals(other.races)) return false

        return true
    }

    override fun hashCode(): Int {
        return races.contentHashCode()
    }
}

@Serializable
data class RaceResultsData(
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
    override val raceTable: RacesWithResultsTableType
): BaseRaceData<RaceWithResultsType>

@Serializable
data class RaceResultsResponse(
    @SerialName("MRData")
    override val data: RaceResultsData
): BaseResponse
