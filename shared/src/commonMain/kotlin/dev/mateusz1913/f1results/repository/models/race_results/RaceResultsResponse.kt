package dev.mateusz1913.f1results.repository.models.race_results

import dev.mateusz1913.f1results.repository.models.circuit.CircuitType
import dev.mateusz1913.f1results.repository.models.constructor.ConstructorType
import dev.mateusz1913.f1results.repository.models.driver.DriverType
import kotlinx.serialization.Serializable

@Serializable
data class DurationType(
    val millis: String?,
    val time: String,
)

@Serializable
data class AverageSpeedType(
    val speed: String,
    val units: String?
)

@Serializable
data class FastestLapType(
    val rank: String,
    val lap: String,
    val time: DurationType,
    val AverageSpeed: AverageSpeedType
)

@Serializable
data class ResultType(
    val number: String?,
    val position: String,
    val positionText: String,
    val points: String?,
    val Driver: DriverType,
    val Constructor: ConstructorType,
    val grid: String,
    val laps: String,
    val status: String,
    val Time: DurationType?,
    val FastestLap: FastestLapType?
)

@Serializable
data class RaceWithResultType(
    val season: String,
    val round: String,
    val url: String,
    val raceName: String,
    val Circuit: CircuitType,
    val date: String,
    val time: String,
    val Results: Array<ResultType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RaceWithResultType

        if (season != other.season) return false
        if (round != other.round) return false
        if (url != other.url) return false
        if (raceName != other.raceName) return false
        if (Circuit != other.Circuit) return false
        if (date != other.date) return false
        if (time != other.time) return false
        if (!Results.contentEquals(other.Results)) return false

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
        result = 31 * result + Results.contentHashCode()
        return result
    }
}

@Serializable
data class RacesWithResultTableType(
    val Races: Array<RaceWithResultType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RacesWithResultTableType

        if (!Races.contentEquals(other.Races)) return false

        return true
    }

    override fun hashCode(): Int {
        return Races.contentHashCode()
    }
}

@Serializable
data class MRDataResultType(
    val series: String?,
    val url: String?,
    val limit: Int?,
    val offset: Int?,
    val total: Int?,
    val RaceTable: RacesWithResultTableType
)

@Serializable
data class RaceResultsResponse(
    val MRData: MRDataResultType
)
