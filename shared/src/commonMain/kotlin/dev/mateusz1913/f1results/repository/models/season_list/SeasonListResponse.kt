package dev.mateusz1913.f1results.repository.models.season_list

import kotlinx.serialization.Serializable

@Serializable
data class SeasonType(
    val season: String,
    val url: String
)

@Serializable
data class SeasonTableType(
    val Seasons: Array<SeasonType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as SeasonTableType

        if (!Seasons.contentEquals(other.Seasons)) return false

        return true
    }

    override fun hashCode(): Int {
        return Seasons.contentHashCode()
    }
}

@Serializable
data class MRDataSeasonListType(
    val series: String?,
    val url: String?,
    val limit: Int?,
    val offset: Int?,
    val total: Int?,
    val SeasonTable: SeasonTableType
)

@Serializable
data class SeasonListResponse(
    val MRData: MRDataSeasonListType
)
