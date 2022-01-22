package dev.mateusz1913.f1results.datasource.cache.standings

import dev.mateusz1913.f1results.datasource.cache.GetConstructorStandingWithConstructorIdAndSeason
import dev.mateusz1913.f1results.datasource.cache.GetConstructorStandingsWithSeasonAndRound
import dev.mateusz1913.f1results.datasource.cache.GetLatestConstructorStandings
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingType
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingsType
import kotlin.jvm.JvmName

fun GetConstructorStandingWithConstructorIdAndSeason.toConstructorStandingType(): ConstructorStandingType {
    return ConstructorStandingType(
        position = "$position",
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

data class ConstructorStandingsCachedData(
    val season: Long,
    val round: Long,
    val position: String,
    val points: String,
    val wins: String,
    val timestamp: Double,
    val constructor_id: String,
    val url: String?,
    val name: String,
    val nationality: String,
    val constructor_timestamp: Double
)

@JvmName("getLatestConstructorStandingsToConstructorStandingsCachedData")
fun GetLatestConstructorStandings.toConstructorStandingsCachedData(): ConstructorStandingsCachedData {
    return ConstructorStandingsCachedData(
        season, round, "$position", points, wins, timestamp, constructor_id, url, name, nationality, constructor_timestamp
    )
}

@JvmName("getConstructorStandingsWithSeasonAndRoundToConstructorStandingsCachedData")
fun GetConstructorStandingsWithSeasonAndRound.toConstructorStandingsCachedData(): ConstructorStandingsCachedData {
    return ConstructorStandingsCachedData(
        season, round, "$position", points, wins, timestamp, constructor_id, url, name, nationality, constructor_timestamp
    )
}

fun List<ConstructorStandingsCachedData>.toConstructorStandingsType(): ConstructorStandingsType {
    return ConstructorStandingsType(
        season = "${this[0].season}",
        round = "${this[0].round}",
        constructorStandings = map {
            ConstructorStandingType(
                position = it.position,
                points = it.points,
                wins = it.wins,
                constructor = ConstructorType(
                    constructorId = it.constructor_id,
                    url = it.url,
                    name = it.name,
                    nationality = it.nationality
                )
            )
        }.toTypedArray()
    )
}
