package dev.mateusz1913.f1results.repository.models.standings

import dev.mateusz1913.f1results.repository.models.constructor.ConstructorType
import dev.mateusz1913.f1results.repository.models.driver.DriverType
import kotlinx.serialization.Serializable

@Serializable
data class DriverStandingType(
    val position: String,
    val positionText: String,
    val points: String,
    val wins: String,
    val Driver: DriverType,
    val Constructors: Array<ConstructorType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DriverStandingType

        if (position != other.position) return false
        if (positionText != other.positionText) return false
        if (points != other.points) return false
        if (wins != other.wins) return false
        if (Driver != other.Driver) return false
        if (!Constructors.contentEquals(other.Constructors)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = position.hashCode()
        result = 31 * result + positionText.hashCode()
        result = 31 * result + points.hashCode()
        result = 31 * result + wins.hashCode()
        result = 31 * result + Driver.hashCode()
        result = 31 * result + Constructors.contentHashCode()
        return result
    }
}

@Serializable
data class ConstructorStandingType(
    val position: String,
    val points: String,
    val wins: String,
    val Constructor: ConstructorType
)

@Serializable
data class StandingType(
    val season: String,
    val round: String?,
    val DriverStandings: Array<DriverStandingType>?,
    val ConstructorStandings: Array<ConstructorStandingType>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as StandingType

        if (season != other.season) return false
        if (round != other.round) return false
        if (DriverStandings != null) {
            if (other.DriverStandings == null) return false
            if (!DriverStandings.contentEquals(other.DriverStandings)) return false
        } else if (other.DriverStandings != null) return false
        if (ConstructorStandings != null) {
            if (other.ConstructorStandings == null) return false
            if (!ConstructorStandings.contentEquals(other.ConstructorStandings)) return false
        } else if (other.ConstructorStandings != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = season.hashCode()
        result = 31 * result + (round?.hashCode() ?: 0)
        result = 31 * result + (DriverStandings?.contentHashCode() ?: 0)
        result = 31 * result + (ConstructorStandings?.contentHashCode() ?: 0)
        return result
    }
}

@Serializable
data class StandingsTableType(
    val StandingsLists: Array<StandingType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as StandingsTableType

        if (!StandingsLists.contentEquals(other.StandingsLists)) return false

        return true
    }

    override fun hashCode(): Int {
        return StandingsLists.contentHashCode()
    }
}

@Serializable
data class MRDataStandingsType(
    val series: String?,
    val url: String?,
    val limit: Int?,
    val offset: Int?,
    val total: Int?,
    val StandingsTable: StandingsTableType
)

@Serializable
data class StandingsResponse(
    val MRData: MRDataStandingsType
)
