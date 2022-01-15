package dev.mateusz1913.f1results.datasource.cache.constructor

import dev.mateusz1913.f1results.datasource.cache.Constructor_Entity
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType

fun Constructor_Entity.toConstructorType(): ConstructorType {
    return ConstructorType(
        constructorId = constructor_id,
        url = url,
        name = name,
        nationality = nationality
    )
}