package dev.mateusz1913.f1results.datasource.data.season_list

import dev.mateusz1913.f1results.datasource.data.base.BaseData
import dev.mateusz1913.f1results.datasource.data.base.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeasonType(
    @SerialName("season")
    val season: String,
    @SerialName("url")
    val url: String
)

@Serializable
data class SeasonTableType(
    @SerialName("Seasons")
    val seasons: Array<SeasonType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as SeasonTableType

        if (!seasons.contentEquals(other.seasons)) return false

        return true
    }

    override fun hashCode(): Int {
        return seasons.contentHashCode()
    }
}

@Serializable
data class SeasonListData(
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
    @SerialName("SeasonTable")
    val seasonTable: SeasonTableType
): BaseData

@Serializable
data class SeasonListResponse(
    @SerialName("MRData")
    override val data: SeasonListData
): BaseResponse
