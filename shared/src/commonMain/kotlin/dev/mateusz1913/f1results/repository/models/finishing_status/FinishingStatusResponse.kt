package dev.mateusz1913.f1results.repository.models.finishing_status

import dev.mateusz1913.f1results.repository.models.driver.DriverTableType
import kotlinx.serialization.Serializable

@Serializable
data class StatusType(
    val statusId: String,
    val count: String,
    val status: String,
)

@Serializable
data class StatusTableType(
    val Status: Array<StatusType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as StatusTableType

        if (!Status.contentEquals(other.Status)) return false

        return true
    }

    override fun hashCode(): Int {
        return Status.contentHashCode()
    }
}

@Serializable
data class MRDataFinishingStatusType(
    val series: String?,
    val url: String?,
    val limit: Int?,
    val offset: Int?,
    val total: Int?,
    val StatusTable: StatusTableType
)

@Serializable
data class FinishingStatusResponse(
    val MRData: MRDataFinishingStatusType
)
