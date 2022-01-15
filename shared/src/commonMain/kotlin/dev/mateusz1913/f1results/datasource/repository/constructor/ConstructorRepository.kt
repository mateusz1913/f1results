package dev.mateusz1913.f1results.datasource.repository.constructor

import dev.mateusz1913.f1results.datasource.cache.constructor.ConstructorCache
import dev.mateusz1913.f1results.datasource.cache.constructor.toConstructorType
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorInfoData
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.datasource.remote.constructor.ConstructorsApi
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class ConstructorRepository(private val constructorsApi: ConstructorsApi, private val constructorCache: ConstructorCache) {
    suspend fun fetchConstructor(constructorId: String): ConstructorType? {
        val constructor = constructorsApi.getSpecificConstructor(constructorId)
        if (constructor != null) {
            constructorCache.insert(constructor)
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

    fun getCachedConstructor(constructorId: String): ConstructorType? {
        val cachedConstructor = try {
            val cached = constructorCache.get(constructorId)
            val currentTimestamp = now().toEpochMilliseconds()
            if (currentTimestamp < cached.timestamp + TIMESTAMP_THRESHOLD) {
                cached
            } else {
                null
            }
        } catch (e: Exception) {
            Napier.d(e.message ?: "No cached constructor", tag = "ConstructorRepository")
            null
        }
        return cachedConstructor?.toConstructorType()
    }

    companion object {
        private const val TIMESTAMP_THRESHOLD: Double = 1000.0 * 60 * 60 * 24
    }
}