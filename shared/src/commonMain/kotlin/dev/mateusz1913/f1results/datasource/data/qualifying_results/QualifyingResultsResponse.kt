package dev.mateusz1913.f1results.datasource.data.qualifying_results

import dev.mateusz1913.f1results.datasource.data.base.BaseResponse
import dev.mateusz1913.f1results.datasource.data.base.race.BaseRaceData
import dev.mateusz1913.f1results.datasource.data.base.race.BaseRaceTableType
import dev.mateusz1913.f1results.datasource.data.base.race.BaseRaceType
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QualifyingResultType(
    @SerialName("number")
    val number: String? = null,
    @SerialName("position")
    val position: String,
    @SerialName("Driver")
    val driver: DriverType,
    @SerialName("Constructor")
    val constructor: ConstructorType,
    @SerialName("Q1")
    val Q1: String,
    @SerialName("Q2")
    val Q2: String? = null,
    @SerialName("Q3")
    val Q3: String? = null
)

@Serializable
data class RaceWithQualifyingResultsType(
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
    @SerialName("QualifyingResults")
    val qualifyingResults: Array<QualifyingResultType>
): BaseRaceType {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RaceWithQualifyingResultsType

        if (season != other.season) return false
        if (round != other.round) return false
        if (url != other.url) return false
        if (raceName != other.raceName) return false
        if (circuit != other.circuit) return false
        if (date != other.date) return false
        if (time != other.time) return false
        if (!qualifyingResults.contentEquals(other.qualifyingResults)) return false

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
        result = 31 * result + qualifyingResults.contentHashCode()
        return result
    }
}

@Serializable
data class RacesWithQualifyingResultsTableType(
    @SerialName("Races")
    override val races: Array<RaceWithQualifyingResultsType>
): BaseRaceTableType<RaceWithQualifyingResultsType> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RacesWithQualifyingResultsTableType

        if (!races.contentEquals(other.races)) return false

        return true
    }

    override fun hashCode(): Int {
        return races.contentHashCode()
    }
}

@Serializable
data class QualifyingResultsData(
    @SerialName("series")
    override val series: String? = null,
    @SerialName("url")
    override val url: String? = null,
    @SerialName("limit")
    override val limit: Int? = null,
    @SerialName("offset")
    override val offset: Int? = null,
    @SerialName("total")
    override val total: Int? = null,
    @SerialName("RaceTable")
    override val raceTable: RacesWithQualifyingResultsTableType
): BaseRaceData<RaceWithQualifyingResultsType>

@Serializable
data class QualifyingResultsResponse(
    @SerialName("MRData")
    override val data: QualifyingResultsData
): BaseResponse
