package dev.mateusz1913.f1results.datasource.cache.standings

import dev.mateusz1913.f1results.datasource.cache.*
import dev.mateusz1913.f1results.datasource.cache.driver.toDriverType
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingType
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds

class DriverStandingsCacheImpl(
    private val queries: DriverStandingsQueries,
    private val constructorQueries: ConstructorQueries,
    private val driverQueries: DriverQueries,
) : DriverStandingsCache {
    override fun getDriverStanding(
        driverId: String,
        season: String
    ): Triple<DriverStandingsCachedData, DriverType, List<GetDriverConstructorsStandingWithDriverIdAndSeason>> {
        val driverStanding =
            queries.getDriverStandingWithDriverIdAndSeason(driverId, season.toLong()).executeAsOne()
        val driver = driverQueries.getDriverById(driverId).executeAsOne().toDriverType()
        val driverConstructors =
            queries.getDriverConstructorsStandingWithDriverIdAndSeason(driverId, season.toLong())
                .executeAsList()
        return Triple(driverStanding.toDriverStandingsCachedData(), driver, driverConstructors)
    }

    override fun getDriverStandings(
        season: String,
        round: String
    ): List<Triple<DriverStandingsCachedData, DriverType, List<GetDriverConstructorsStandingWithDriverIdAndSeason>>> {
        val driverStandings =
            queries.getDriverStandingsWithSeasonAndRound(season.toLong(), round.toLong())
                .executeAsList()
        return driverStandings.map { standing ->
            val driver =
                driverQueries.getDriverById(standing.driver_id).executeAsOne().toDriverType()
            val driverConstructors =
                queries.getDriverConstructorsStandingWithDriverIdAndSeason(
                    standing.driver_id,
                    standing.season
                )
                    .executeAsList()
            Triple(
                standing.toDriverStandingsCachedData(),
                driver,
                driverConstructors
            )
        }
    }

    override fun getLatestDriverStandings(): List<Triple<DriverStandingsCachedData, DriverType, List<GetDriverConstructorsStandingWithDriverIdAndSeason>>> {
        val driverStandings = queries.getLatestDriverStandings().executeAsList()
        return driverStandings.map { standing ->
            val driver =
                driverQueries.getDriverById(standing.driver_id).executeAsOne().toDriverType()
            val driverConstructors =
                queries.getDriverConstructorsStandingWithDriverIdAndSeason(
                    standing.driver_id,
                    standing.season
                )
                    .executeAsList()
            Triple(
                standing.toDriverStandingsCachedData(),
                driver,
                driverConstructors
            )
        }
    }

    override fun insertDriverStanding(
        driverStanding: DriverStandingType,
        season: String,
        round: String
    ) {
        queries.transaction {
            queries.insertDriverStanding(
                driver_id = driverStanding.driver.driverId,
                season = season.toLong(),
                round = round.toLong(),
                position = driverStanding.position,
                position_text = driverStanding.positionText,
                points = driverStanding.points,
                wins = driverStanding.wins,
                timestamp = now().toEpochMilliseconds()
            )

            driverQueries.insertDriver(
                driver_id = driverStanding.driver.driverId,
                permanent_number = driverStanding.driver.permanentNumber,
                code = driverStanding.driver.code,
                url = driverStanding.driver.url,
                given_name = driverStanding.driver.givenName,
                family_name = driverStanding.driver.familyName,
                date_of_birth = driverStanding.driver.dateOfBirth,
                nationality = driverStanding.driver.nationality,
                timestamp = now().toEpochMilliseconds()
            )

            driverStanding.constructors.forEach {
                constructorQueries.insertConstructor(
                    constructor_id = it.constructorId,
                    url = it.url,
                    nationality = it.nationality,
                    name = it.name,
                    timestamp = now().toEpochMilliseconds()
                )
                queries.insertDriverConstructorsStanding(
                    driver_id = driverStanding.driver.driverId,
                    constructor_id = it.constructorId,
                    season = season.toLong(),
                )
            }
        }
    }
}