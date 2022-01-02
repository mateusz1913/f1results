package dev.mateusz1913.f1results.datasource.repository.constructor

import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorInfoData
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.datasource.remote.constructor.ConstructorsApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ConstructorRepository: KoinComponent {
    private val constructorsApi: ConstructorsApi by inject()

    suspend fun fetchConstructor(constructorId: String): ConstructorType? {
        return constructorsApi.getSpecificConstructor(constructorId)
    }

    suspend fun fetchConstructorsList(
        limit: Int? = null,
        offset: Int? = null,
        season: String? = null,
        round: String? = null,
        circuitId: String? = null,
        constructorStandings: Int? = null,
        driverId: String? = null,
        grid: Int? = null,
        results: Int? = null,
        fastest: Int? = null,
        statusId: String? = null,
    ): ConstructorInfoData? {
        return constructorsApi.getConstructors(
            limit,
            offset,
            season,
            round,
            circuitId,
            constructorStandings,
            driverId,
            grid,
            results,
            fastest,
            statusId
        )
    }
}