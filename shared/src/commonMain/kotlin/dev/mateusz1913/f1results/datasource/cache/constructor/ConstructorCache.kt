package dev.mateusz1913.f1results.datasource.cache.constructor

import dev.mateusz1913.f1results.datasource.cache.Constructor_Entity
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType

interface ConstructorCache {
    fun get(constructorId: String): Constructor_Entity
    fun insert(constructor: ConstructorType)
    fun insert(constructors: Array<ConstructorType>)
}
