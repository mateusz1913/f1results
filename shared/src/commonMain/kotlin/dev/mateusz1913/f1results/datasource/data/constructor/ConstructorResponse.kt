package dev.mateusz1913.f1results.datasource.data.constructor

import dev.mateusz1913.f1results.datasource.data.base.BaseData
import dev.mateusz1913.f1results.datasource.data.base.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConstructorType(
    @SerialName("constructorId")
    val constructorId: String,
    @SerialName("url")
    val url: String? = null,
    @SerialName("name")
    val name: String,
    @SerialName("nationality")
    val nationality: String,
)

@Serializable
data class ConstructorTableType(
    @SerialName("Constructors")
    val constructors: Array<ConstructorType>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ConstructorTableType

        if (!constructors.contentEquals(other.constructors)) return false

        return true
    }

    override fun hashCode(): Int {
        return constructors.contentHashCode()
    }
}

@Serializable
data class ConstructorInfoData(
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
    @SerialName("ConstructorTable")
    val constructorTable: ConstructorTableType
): BaseData

@Serializable
data class ConstructorResponse(
    @SerialName("MRData")
    override val data: ConstructorInfoData
): BaseResponse
