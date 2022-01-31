package dev.mateusz1913.f1results.datasource.cache.race_results

import dev.mateusz1913.f1results.datasource.cache.*
import dev.mateusz1913.f1results.datasource.cache.race_schedule.RaceScheduleCachedData
import dev.mateusz1913.f1results.datasource.cache.race_schedule.toRaceScheduleCachedData
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class RaceResultsCacheImpl(
    private val raceResultsQueries: RaceResultsQueries,
    private val raceScheduleQueries: RaceQueries,
    private val circuitQueries: CircuitQueries,
    private val driverQueries: DriverQueries,
    private val constructorQueries: ConstructorQueries
) : RaceResultsCache {
    override fun getConstructorSeasonResults(
        season: String,
        constructorId: String
    ): Array<RaceWithResultsType> {
        return raceResultsQueries.getConstructorSeasonRaceResults(
            constructorId,
            season.toLong()
        ).executeAsList().toRaceResultsArray()
    }

    override fun getDriverSeasonResults(
        season: String,
        driverId: String
    ): Array<RaceWithResultsType> {
        return raceResultsQueries.getDriverSeasonRaceResults(
            driverId,
            season.toLong()
        ).executeAsList().toRaceResultsArray()
    }

    override fun getLatestRaceResults(): Pair<RaceScheduleCachedData, List<RaceResultsCachedData>> {
        val raceSchedule =
            raceScheduleQueries.getLatestRace().executeAsOne().toRaceScheduleCachedData()
        val raceResults = raceResultsQueries.getLastRaceResults().executeAsList()
            .map { it.toRaceResultsCachedData() }
        return Pair(raceSchedule, raceResults)
    }

    override fun getRaceResultsWithSeasonAndRound(
        season: String,
        round: String
    ): Pair<RaceScheduleCachedData, List<RaceResultsCachedData>> {
        val raceSchedule = raceScheduleQueries.getRaceWithRaceId("$season/$round").executeAsOne()
            .toRaceScheduleCachedData()
        val raceResults =
            raceResultsQueries.getRaceResultsWithRaceId("$season/$round").executeAsList()
                .map { it.toRaceResultsCachedData() }
        return Pair(raceSchedule, raceResults)
    }

    override fun insertRaceResults(raceResults: RaceWithResultsType): Boolean {
        return try {
            raceResultsQueries.transactionWithResult {
                raceScheduleQueries.insertRace(
                    race_id = "${raceResults.season}/${raceResults.round}",
                    season = raceResults.season.toLong(),
                    round = raceResults.round.toLong(),
                    url = raceResults.url,
                    race_name = raceResults.raceName,
                    circuit_id = raceResults.circuit.circuitId,
                    date = raceResults.date,
                    time = raceResults.time,
                    timestamp = now().toEpochMilliseconds()
                )
                circuitQueries.insertCircuit(
                    circuit_id = raceResults.circuit.circuitId,
                    url = raceResults.circuit.url,
                    circuit_name = raceResults.circuit.circuitName,
                    country = raceResults.circuit.location.country,
                    locality = raceResults.circuit.location.locality,
                    alt = raceResults.circuit.location.alt,
                    lat = raceResults.circuit.location.lat,
                    long = raceResults.circuit.location.long,
                    timestamp = now().toEpochMilliseconds()
                )
                raceResults.results.forEach { result ->
                    raceResultsQueries.insertRaceResults(
                        id = "${raceResults.season}/${raceResults.round}/${result.driver.driverId}",
                        race_id = "${raceResults.season}/${raceResults.round}",
                        driver_id = result.driver.driverId,
                        constructor_id = result.constructor.constructorId,
                        number = result.number,
                        position = result.position.toLong(),
                        position_text = result.positionText,
                        points = result.points,
                        grid = result.grid,
                        laps = result.laps,
                        status = result.status,
                        time = result.time?.time,
                        milliseconds = result.time?.millis,
                        fastest_lap = result.fastestLap?.lap,
                        rank = result.fastestLap?.rank,
                        fastest_lap_time = result.fastestLap?.time?.time,
                        fastest_lap_milliseconds = result.fastestLap?.time?.millis,
                        fastest_lap_speed = result.fastestLap?.averageSpeed?.speed,
                        fastest_lap_speed_units = result.fastestLap?.averageSpeed?.units,
                        timestamp = now().toEpochMilliseconds()
                    )
                    driverQueries.insertDriver(
                        driver_id = result.driver.driverId,
                        permanent_number = result.driver.permanentNumber,
                        code = result.driver.code,
                        url = result.driver.url,
                        given_name = result.driver.givenName,
                        family_name = result.driver.familyName,
                        date_of_birth = result.driver.dateOfBirth,
                        nationality = result.driver.nationality,
                        timestamp = now().toEpochMilliseconds()
                    )
                    constructorQueries.insertConstructor(
                        constructor_id = result.constructor.constructorId,
                        url = result.constructor.url,
                        name = result.constructor.name,
                        nationality = result.constructor.nationality,
                        timestamp = now().toEpochMilliseconds()
                    )
                }
                true
            }
        } catch (e: Exception) {
            Napier.w("insertRaceResults - ${e.message}", e, "RaceResultsCache")
            false
        }
    }
}
