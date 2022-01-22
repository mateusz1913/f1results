package dev.mateusz1913.f1results.datasource.cache.constructor

import dev.mateusz1913.f1results.datasource.cache.ConstructorQueries
import dev.mateusz1913.f1results.datasource.cache.Constructor_Entity
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class ConstructorCacheImpl(private val queries: ConstructorQueries) : ConstructorCache {
    override fun get(constructorId: String): Constructor_Entity {
        return queries.getConstructorById(constructorId).executeAsOne()
    }

    override fun insert(constructor: ConstructorType): Boolean {
        return try {
            queries.transactionWithResult {
                queries.insertConstructor(
                    constructor_id = constructor.constructorId,
                    url = constructor.url,
                    name = constructor.name,
                    nationality = constructor.nationality,
                    timestamp = now().toEpochMilliseconds()
                )
                true
            }
        } catch (e: Exception) {
            Napier.w("insertConstructor - ${e.message}", e, "ConstructorCache")
            false
        }
    }

    override fun insert(constructors: Array<ConstructorType>): Boolean {
        return try {
            queries.transactionWithResult {
                constructors.map { constructor ->
                    queries.insertConstructor(
                        constructor_id = constructor.constructorId,
                        url = constructor.url,
                        name = constructor.name,
                        nationality = constructor.nationality,
                        timestamp = now().toEpochMilliseconds()
                    )
                    true
                }.reduce { acc, curr ->
                    acc && curr
                }
            }
        } catch (e: Exception) {
            Napier.w("insertConstructors - ${e.message}", e, "ConstructorCache")
            false
        }
    }
}