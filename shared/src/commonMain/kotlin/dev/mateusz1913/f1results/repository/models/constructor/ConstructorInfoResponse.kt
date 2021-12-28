package dev.mateusz1913.f1results.repository.models.constructor

import kotlinx.serialization.Serializable

@Serializable
data class ConstructorType(
    val constructorId: String,
    val url: String?,
    val name: String,
    val nationality: String,
)

@Serializable
data class ConstructorTableType(
    val Constructors: Array<ConstructorType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ConstructorTableType

        if (!Constructors.contentEquals(other.Constructors)) return false

        return true
    }

    override fun hashCode(): Int {
        return Constructors.contentHashCode()
    }
}

@Serializable
data class MRDataConstructorInfoType(
    val series: String?,
    val url: String?,
    val limit: Int?,
    val offset: Int?,
    val total: Int?,
    val ConstructorTable: ConstructorTableType
)

@Serializable
data class ConstructorInfoResponse(
    val MRData: MRDataConstructorInfoType
)
