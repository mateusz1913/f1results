package dev.mateusz1913.f1results.repository.models.standings

import dev.mateusz1913.f1results.repository.models.base.BaseResponse
import dev.mateusz1913.f1results.repository.models.base.standings.BaseStandingsData
import dev.mateusz1913.f1results.repository.models.base.standings.BaseStandingsTableType
import dev.mateusz1913.f1results.repository.models.base.standings.BaseStandingsType
import dev.mateusz1913.f1results.repository.models.constructor.ConstructorType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConstructorStandingType(
    @SerialName("position")
    val position: String,
    @SerialName("points")
    val points: String,
    @SerialName("wins")
    val wins: String,
    @SerialName("Constructor")
    val constructor: ConstructorType
)

@Serializable
data class ConstructorStandingsType(
    @SerialName("season")
    override val season: String,
    @SerialName("round")
    override val round: String?,
    @SerialName("ConstructorStandings")
    val constructorStandings: Array<ConstructorStandingType>
): BaseStandingsType {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ConstructorStandingsType

        if (season != other.season) return false
        if (round != other.round) return false
        if (!constructorStandings.contentEquals(other.constructorStandings)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = season.hashCode()
        result = 31 * result + (round?.hashCode() ?: 0)
        result = 31 * result + (constructorStandings.contentHashCode())
        return result
    }
}

@Serializable
data class ConstructorStandingsTableType(
    @SerialName("StandingsLists")
    override val standingsLists: Array<ConstructorStandingsType>
): BaseStandingsTableType<ConstructorStandingsType> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ConstructorStandingsTableType

        if (!standingsLists.contentEquals(other.standingsLists)) return false

        return true
    }

    override fun hashCode(): Int {
        return standingsLists.contentHashCode()
    }
}

@Serializable
data class ConstructorStandingsData(
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
    @SerialName("StandingsTable")
    override val standingsTable: ConstructorStandingsTableType
): BaseStandingsData<ConstructorStandingsType>

@Serializable
data class ConstructorStandingsResponse(
    @SerialName("MRData")
    override val data: ConstructorStandingsData
): BaseResponse
