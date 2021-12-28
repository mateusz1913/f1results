package dev.mateusz1913.f1results.repository.models.driver

import kotlinx.serialization.Serializable

@Serializable
data class DriverType(
    val driverId: String,
    val permanentNumber: String?,
    val code: String,
    val url: String?,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String?,
    val nationality: String?
)

@Serializable
data class DriverTableType(
    val Drivers: Array<DriverType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DriverTableType

        if (!Drivers.contentEquals(other.Drivers)) return false

        return true
    }

    override fun hashCode(): Int {
        return Drivers.contentHashCode()
    }
}

@Serializable
data class MRDataDriverInfoType(
    val series: String?,
    val url: String?,
    val limit: Int?,
    val offset: Int?,
    val total: Int?,
    val DriverTable: DriverTableType
)

@Serializable
data class DriverInfoResponse(
    val MRData: MRDataDriverInfoType
)
