package dev.mateusz1913.f1results.datasource.data.standings

import dev.mateusz1913.f1results.datasource.data.base.BaseResponse
import dev.mateusz1913.f1results.datasource.data.base.standings.BaseStandingsData
import dev.mateusz1913.f1results.datasource.data.base.standings.BaseStandingsTableType
import dev.mateusz1913.f1results.datasource.data.base.standings.BaseStandingsType
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverStandingType(
    @SerialName("position")
    val position: String,
    @SerialName("positionText")
    val positionText: String,
    @SerialName("points")
    val points: String,
    @SerialName("wins")
    val wins: String,
    @SerialName("Driver")
    val driver: DriverType,
    @SerialName("Constructors")
    val constructors: Array<ConstructorType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DriverStandingType

        if (position != other.position) return false
        if (positionText != other.positionText) return false
        if (points != other.points) return false
        if (wins != other.wins) return false
        if (driver != other.driver) return false
        if (!constructors.contentEquals(other.constructors)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = position.hashCode()
        result = 31 * result + positionText.hashCode()
        result = 31 * result + points.hashCode()
        result = 31 * result + wins.hashCode()
        result = 31 * result + driver.hashCode()
        result = 31 * result + constructors.contentHashCode()
        return result
    }
}

@Serializable
data class DriverStandingsType(
    @SerialName("season")
    override val season: String,
    @SerialName("round")
    override val round: String? = null,
    @SerialName("DriverStandings")
    val driverStandings: Array<DriverStandingType>,
): BaseStandingsType {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DriverStandingsType

        if (season != other.season) return false
        if (round != other.round) return false
        if (!driverStandings.contentEquals(other.driverStandings)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = season.hashCode()
        result = 31 * result + (round?.hashCode() ?: 0)
        result = 31 * result + (driverStandings.contentHashCode())
        return result
    }
}

@Serializable
data class DriverStandingsTableType(
    @SerialName("StandingsLists")
    override val standingsLists: Array<DriverStandingsType>
): BaseStandingsTableType<DriverStandingsType> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DriverStandingsTableType

        if (!standingsLists.contentEquals(other.standingsLists)) return false

        return true
    }

    override fun hashCode(): Int {
        return standingsLists.contentHashCode()
    }
}

@Serializable
data class DriverStandingsData(
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
    @SerialName("StandingsTable")
    override val standingsTable: DriverStandingsTableType
): BaseStandingsData<DriverStandingsType>

@Serializable
data class DriverStandingsResponse(
    @SerialName("MRData")
    override val data: DriverStandingsData
): BaseResponse
