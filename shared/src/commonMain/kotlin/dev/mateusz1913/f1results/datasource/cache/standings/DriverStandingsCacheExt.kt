package dev.mateusz1913.f1results.datasource.cache.standings

import dev.mateusz1913.f1results.datasource.cache.GetDriverConstructorsStandingWithDriverIdAndSeason
import dev.mateusz1913.f1results.datasource.cache.GetDriverStandingWithDriverIdAndSeason
import dev.mateusz1913.f1results.datasource.cache.GetDriverStandingsWithSeasonAndRound
import dev.mateusz1913.f1results.datasource.cache.GetLatestDriverStandings
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingType
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingsType
import kotlin.jvm.JvmName

data class DriverStandingsCachedData(
    val season: Long,
    val driver_id: String,
    val round: Long,
    val position: String,
    val position_text: String,
    val points: String,
    val wins: String,
    val timestamp: Double
)

@JvmName("getDriverStandingWithDriverIdAndSeasonToDriverStandingsCachedData")
fun GetDriverStandingWithDriverIdAndSeason.toDriverStandingsCachedData(): DriverStandingsCachedData {
    return DriverStandingsCachedData(
        season, driver_id, round, "$position", position_text, points, wins, timestamp
    )
}

@JvmName("getDriverStandingsWithSeasonAndRoundToDriverStandingsCachedData")
fun GetDriverStandingsWithSeasonAndRound.toDriverStandingsCachedData(): DriverStandingsCachedData {
    return DriverStandingsCachedData(
        season, driver_id, round, "$position", position_text, points, wins, timestamp
    )
}

@JvmName("getLatestDriverStandingsToDriverStandingsCachedData")
fun GetLatestDriverStandings.toDriverStandingsCachedData(): DriverStandingsCachedData {
    return DriverStandingsCachedData(
        season, driver_id, round, "$position", position_text, points, wins, timestamp
    )
}

fun Triple<DriverStandingsCachedData, DriverType, List<GetDriverConstructorsStandingWithDriverIdAndSeason>>.toDriverStandingType(): DriverStandingType {
    val (driverStanding, driver, constructors) = this
    return DriverStandingType(
        position = driverStanding.position,
        positionText = driverStanding.position_text,
        points = driverStanding.points,
        wins = driverStanding.wins,
        driver = driver,
        constructors = constructors.map { constructor ->
            ConstructorType(
                constructorId = constructor.constructor_id,
                url = constructor.url,
                name = constructor.name,
                nationality = constructor.nationality
            )
        }.toTypedArray()
    )
}

fun List<Triple<DriverStandingsCachedData, DriverType, List<GetDriverConstructorsStandingWithDriverIdAndSeason>>>.toDriverStandingsType(): DriverStandingsType {
    val (firstDriverStanding) = this[0]
    return DriverStandingsType(
        season = "${firstDriverStanding.season}",
        round = "${firstDriverStanding.round}",
        driverStandings = map { it.toDriverStandingType() }.toTypedArray()
    )
}
