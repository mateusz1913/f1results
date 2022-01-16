package dev.mateusz1913.f1results.datasource.cache.standings

import dev.mateusz1913.f1results.datasource.cache.GetConstructorStandingWithConstructorIdAndSeason
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingType

fun GetConstructorStandingWithConstructorIdAndSeason.toConstructorStandingType(): ConstructorStandingType {
    return ConstructorStandingType(
        position = position,
        points = points,
        wins = wins,
        constructor = ConstructorType(
            constructorId = constructor_id,
            url = url,
            name = name,
            nationality = nationality
        )
    )
}
