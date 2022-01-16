package dev.mateusz1913.f1results.datasource.cache.standings

import dev.mateusz1913.f1results.datasource.cache.ConstructorQueries
import dev.mateusz1913.f1results.datasource.cache.ConstructorStandingsQueries
import dev.mateusz1913.f1results.datasource.cache.GetConstructorStandingWithConstructorIdAndSeason
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingType
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds

class ConstructorStandingsCacheImpl(
    private val constructorStandingsQueries: ConstructorStandingsQueries,
    private val constructorQueries: ConstructorQueries
) : ConstructorStandingsCache {
    override fun getConstructorStanding(
        constructorId: String,
        season: String
    ): GetConstructorStandingWithConstructorIdAndSeason {
        return constructorStandingsQueries.getConstructorStandingWithConstructorIdAndSeason(
            constructorId,
            season
        ).executeAsOne()
    }

    override fun insertConstructorStanding(
        constructorStanding: ConstructorStandingType,
        season: String
    ) {
        constructorStandingsQueries.transaction {
            constructorStandingsQueries.insertConstructorStanding(
                constructor_id = constructorStanding.constructor.constructorId,
                season = season,
                points = constructorStanding.points,
                position = constructorStanding.position,
                wins = constructorStanding.wins,
                timestamp = now().toEpochMilliseconds()
            )
            constructorQueries.insertConstructor(
                constructor_id = constructorStanding.constructor.constructorId,
                url = constructorStanding.constructor.url,
                name = constructorStanding.constructor.name,
                nationality = constructorStanding.constructor.nationality,
                timestamp = now().toEpochMilliseconds()
            )
        }
    }
}