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
    ): Triple<GetDriverStandingWithDriverIdAndSeason, DriverType, List<GetDriverConstructorsStandingWithDriverIdAndSeason>> {
        val driverStanding =
            queries.getDriverStandingWithDriverIdAndSeason(driverId, season).executeAsOne()
        val driver = driverQueries.getDriverById(driverId).executeAsOne().toDriverType()
        val driverConstructors =
            queries.getDriverConstructorsStandingWithDriverIdAndSeason(driverId, season)
                .executeAsList()
        return Triple(driverStanding, driver, driverConstructors)
    }

    override fun insertDriverStanding(
        driverStanding: DriverStandingType,
        driverId: String,
        season: String
    ) {
        queries.transaction {
            queries.insertDriverStanding(
                driver_id = driverId,
                season = season,
                position = driverStanding.position,
                position_text = driverStanding.positionText,
                points = driverStanding.points,
                wins = driverStanding.wins,
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
                    driver_id = driverId,
                    constructor_id = it.constructorId,
                    season = season
                )
            }
        }
    }
}