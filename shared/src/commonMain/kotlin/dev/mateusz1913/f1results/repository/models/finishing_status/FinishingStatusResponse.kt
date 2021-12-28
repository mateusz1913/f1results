package dev.mateusz1913.f1results.repository.models.finishing_status

import dev.mateusz1913.f1results.repository.models.base.BaseResponse
import dev.mateusz1913.f1results.repository.models.base.BaseData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusType(
    @SerialName("statusId")
    val statusId: String,
    @SerialName("count")
    val count: String,
    @SerialName("status")
    val status: String,
)

@Serializable
data class StatusTableType(
    @SerialName("Status")
    val status: Array<StatusType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as StatusTableType

        if (!status.contentEquals(other.status)) return false

        return true
    }

    override fun hashCode(): Int {
        return status.contentHashCode()
    }
}

@Serializable
data class FinishingStatusData(
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
    @SerialName("StatusTable")
    val statusTable: StatusTableType
): BaseData

@Serializable
data class FinishingStatusResponse(
    @SerialName("MRData")
    override val data: FinishingStatusData
): BaseResponse
