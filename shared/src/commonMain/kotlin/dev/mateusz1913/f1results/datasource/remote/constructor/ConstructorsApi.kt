package dev.mateusz1913.f1results.datasource.remote.constructor

import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorInfoData
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType

interface ConstructorsApi {
    suspend fun getSpecificConstructor(constructorId: String): ConstructorType?

    suspend fun getConstructors(
        limit: Int?,
        offset: Int?,
        season: String?,
        round: String?,
        circuitId: String?,
        constructorStandings: Int?,
        driverId: String?,
        grid: Int?,
        results: Int?,
        fastest: Int?,
        statusId: String?,
    ): ConstructorInfoData?
}