package dev.mateusz1913.f1results.datasource.cache.standings

import dev.mateusz1913.f1results.datasource.cache.*
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
            season.toLong()
        ).executeAsOne()
    }

    override fun getConstructorStandings(
        season: String,
        round: String
    ): List<GetConstructorStandingsWithSeasonAndRound> {
        return constructorStandingsQueries.getConstructorStandingsWithSeasonAndRound(
            season.toLong(),
            round.toLong()
        ).executeAsList()
    }

    override fun getLatestConstructorStandings(): List<GetLatestConstructorStandings> {
        return constructorStandingsQueries.getLatestConstructorStandings().executeAsList()
    }

    override fun insertConstructorStanding(
        constructorStanding: ConstructorStandingType,
        season: String,
        round: String
    ) {
        constructorStandingsQueries.transaction {
            constructorStandingsQueries.insertConstructorStanding(
                constructor_id = constructorStanding.constructor.constructorId,
                season = season.toLong(),
                round = round.toLong(),
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