package dev.mateusz1913.f1results.repository.models.qualifying_results

import dev.mateusz1913.f1results.repository.models.circuit.CircuitType
import dev.mateusz1913.f1results.repository.models.constructor.ConstructorType
import dev.mateusz1913.f1results.repository.models.driver.DriverType
import kotlinx.serialization.Serializable

@Serializable
data class QualifyingResultType(
    val number: String?,
    val position: String,
    val Driver: DriverType,
    val Constructor: ConstructorType,
    val Q1: String,
    val Q2: String?,
    val Q3: String?
)

@Serializable
data class RaceWithQualifyingResultType(
    val season: String,
    val round: String,
    val url: String,
    val raceName: String,
    val Circuit: CircuitType,
    val date: String,
    val time: String,
    val QualifyingResults: Array<QualifyingResultType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RaceWithQualifyingResultType

        if (season != other.season) return false
        if (round != other.round) return false
        if (url != other.url) return false
        if (raceName != other.raceName) return false
        if (Circuit != other.Circuit) return false
        if (date != other.date) return false
        if (time != other.time) return false
        if (!QualifyingResults.contentEquals(other.QualifyingResults)) return false

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
        result = 31 * result + QualifyingResults.contentHashCode()
        return result
    }
}

@Serializable
data class RacesWithQualifyingResultTableType(
    val Races: Array<RaceWithQualifyingResultType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RacesWithQualifyingResultTableType

        if (!Races.contentEquals(other.Races)) return false

        return true
    }

    override fun hashCode(): Int {
        return Races.contentHashCode()
    }
}

@Serializable
data class MRDataQualifyingResultType(
    val series: String?,
    val url: String?,
    val limit: Int?,
    val offset: Int?,
    val total: Int?,
    val RaceTable: RacesWithQualifyingResultTableType
)

@Serializable
data class QualifyingResultsResponse(
    val MRData: MRDataQualifyingResultType
)
