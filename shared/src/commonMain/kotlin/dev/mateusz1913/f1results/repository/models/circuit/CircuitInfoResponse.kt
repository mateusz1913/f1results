package dev.mateusz1913.f1results.repository.models.circuit

import kotlinx.serialization.Serializable

@Serializable
data class LocationType(
    val alt: String?,
    val lat: String?,
    val long: String?,
    val locality: String,
    val country: String,
)

@Serializable
data class CircuitType(
    val circuitId: String,
    val url: String,
    val circuitName: String,
    val Location: LocationType
)

@Serializable
data class CircuitTableType(
    val Circuits: Array<CircuitType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as CircuitTableType

        if (!Circuits.contentEquals(other.Circuits)) return false

        return true
    }

    override fun hashCode(): Int {
        return Circuits.contentHashCode()
    }
}

@Serializable
data class MRDataCircuitInfoType(
    val series: String?,
    val url: String?,
    val limit: Int?,
    val offset: Int?,
    val total: Int?,
    val CircuitTable: CircuitTableType
)

@Serializable
data class CircuitInfoResponse(
    val MRData: MRDataCircuitInfoType
)
