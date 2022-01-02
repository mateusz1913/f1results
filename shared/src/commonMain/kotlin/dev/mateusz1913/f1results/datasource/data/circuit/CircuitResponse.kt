package dev.mateusz1913.f1results.datasource.data.circuit

import dev.mateusz1913.f1results.datasource.data.base.BaseData
import dev.mateusz1913.f1results.datasource.data.base.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationType(
    @SerialName("alt")
    val alt: String? = null,
    @SerialName("lat")
    val lat: String? = null,
    @SerialName("long")
    val long: String? = null,
    @SerialName("locality")
    val locality: String,
    @SerialName("country")
    val country: String,
)

@Serializable
data class CircuitType(
    @SerialName("circuitId")
    val circuitId: String,
    @SerialName("url")
    val url: String,
    @SerialName("circuitName")
    val circuitName: String,
    @SerialName("Location")
    val location: LocationType
)

@Serializable
data class CircuitTableType(
    @SerialName("Circuits")
    val circuits: Array<CircuitType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as CircuitTableType

        if (!circuits.contentEquals(other.circuits)) return false

        return true
    }

    override fun hashCode(): Int {
        return circuits.contentHashCode()
    }
}

@Serializable
data class CircuitInfoData(
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
    @SerialName("CircuitTable")
    val circuitTable: CircuitTableType
): BaseData

@Serializable
data class CircuitResponse(
    @SerialName("MRData")
    override val data: CircuitInfoData
): BaseResponse
