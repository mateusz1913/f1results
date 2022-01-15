package dev.mateusz1913.f1results.datasource.cache.constructor

import dev.mateusz1913.f1results.datasource.cache.ConstructorQueries
import dev.mateusz1913.f1results.datasource.cache.Constructor_Entity
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds

class ConstructorCacheImpl(private val queries: ConstructorQueries): ConstructorCache {
    override fun get(constructorId: String): Constructor_Entity {
        return queries.getConstructorById(constructorId).executeAsOne()
    }

    override fun insert(constructor: ConstructorType) {
        queries.insertConstructor(
            constructor_id = constructor.constructorId,
            url = constructor.url,
            name = constructor.name,
            nationality = constructor.nationality,
            timestamp = now().toEpochMilliseconds()
        )
    }

    override fun insert(constructors: Array<ConstructorType>) {
        constructors.forEach { constructor ->
            insert(constructor)
        }
    }
}