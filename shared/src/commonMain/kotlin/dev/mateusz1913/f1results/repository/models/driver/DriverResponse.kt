package dev.mateusz1913.f1results.repository.models.driver

import dev.mateusz1913.f1results.repository.models.base.BaseResponse
import dev.mateusz1913.f1results.repository.models.base.BaseData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverType(
    @SerialName("driverId")
    val driverId: String,
    @SerialName("permanentNumber")
    val permanentNumber: String?,
    @SerialName("code")
    val code: String,
    @SerialName("url")
    val url: String?,
    @SerialName("givenName")
    val givenName: String,
    @SerialName("familyName")
    val familyName: String,
    @SerialName("dateOfBirth")
    val dateOfBirth: String?,
    @SerialName("nationality")
    val nationality: String?
)

@Serializable
data class DriverTableType(
    @SerialName("Drivers")
    val drivers: Array<DriverType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DriverTableType

        if (!drivers.contentEquals(other.drivers)) return false

        return true
    }

    override fun hashCode(): Int {
        return drivers.contentHashCode()
    }
}

@Serializable
data class DriverInfoData(
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
    @SerialName("DriverTable")
    val driverTable: DriverTableType
): BaseData

@Serializable
data class DriverResponse(
    @SerialName("MRData")
    override val data: DriverInfoData
): BaseResponse
