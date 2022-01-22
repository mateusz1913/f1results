package dev.mateusz1913.f1results.datasource.repository.constructor

import dev.mateusz1913.f1results.datasource.cache.constructor.ConstructorCache
import dev.mateusz1913.f1results.datasource.cache.constructor.toConstructorType
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.RequestsTimestampsCache
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.getConstructorRequest
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorInfoData
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.datasource.remote.constructor.ConstructorsApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class ConstructorRepository(
    private val constructorsApi: ConstructorsApi,
    private val constructorCache: ConstructorCache,
    private val requestsTimestampsCache: RequestsTimestampsCache
) {
    suspend fun fetchConstructor(constructorId: String): ConstructorType? {
        val constructor = constructorsApi.getSpecificConstructor(constructorId)
        if (constructor != null) {
            val succeeded = constructorCache.insert(constructor)
            if (succeeded) {
                requestsTimestampsCache.insertRequestTimestamp(
                    getConstructorRequest(constructorId),
                    now().toEpochMilliseconds()
                )
            }
        }
        return constructor
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
        val constructorInfoData = constructorsApi.getConstructors(
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
        if (constructorInfoData != null) {
            val succeeded =
                constructorCache.insert(constructorInfoData.constructorTable.constructors)
            if (succeeded) {
                constructorInfoData.constructorTable.constructors.forEach {
                    requestsTimestampsCache.insertRequestTimestamp(
                        it.constructorId,
                        now().toEpochMilliseconds()
                    )
                }
            }
        }
        return constructorInfoData
    }

    fun getCachedConstructor(constructorId: String): ConstructorType? {
        val currentTimestamp = now().toEpochMilliseconds()
        val timestamp =
            requestsTimestampsCache.getRequestTimestamp(getConstructorRequest(constructorId))?.timestamp
        if (timestamp == null || currentTimestamp > timestamp + TIMESTAMP_THRESHOLD) {
            return null
        }
        try {
            val cached = constructorCache.get(constructorId)
            if (currentTimestamp > cached.timestamp + TIMESTAMP_THRESHOLD) {
                return null
            }
            return cached.toConstructorType()
        } catch (e: Exception) {
            Napier.w("No cached constructor ${e.message}", e, "ConstructorRepository")
            return null
        }
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}