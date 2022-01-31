package dev.mateusz1913.f1results.datasource.cache.race_results

import dev.mateusz1913.f1results.datasource.cache.*
import dev.mateusz1913.f1results.datasource.cache.race_schedule.RaceScheduleCachedData
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.data.circuit.LocationType
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import dev.mateusz1913.f1results.datasource.data.race_results.*
import kotlin.jvm.JvmName

data class RaceResultsCachedData(
    val race_id: String,
    val driver_id: String,
    val driver_nationality: String?,
    val driver_url: String?,
    val driver_date_of_birth: String?,
    val driver_family_name: String,
    val driver_given_name: String,
    val driver_permanent_number: String?,
    val driver_code: String,
    val driver_timestamp: Double,
    val constructor_id: String,
    val constructor_url: String?,
    val constructor_nationality: String,
    val constructor_name: String,
    val constructor_timestamp: Double,
    val number: String?,
    val position: String,
    val position_text: String,
    val points: String?,
    val grid: String,
    val laps: String,
    val status: String,
    val time: String?,
    val milliseconds: String?,
    val fastest_lap: String?,
    val rank: String?,
    val fastest_lap_time: String?,
    val fastest_lap_milliseconds: String?,
    val fastest_lap_speed: String?,
    val fastest_lap_speed_units: String?,
    val timestamp: Double
)

@JvmName("getConstructorSeasonRaceResultsToRaceResultsCachedData")
fun GetConstructorSeasonRaceResults.toRaceResultsCachedData(): RaceResultsCachedData {
    return RaceResultsCachedData(
        race_id,
        driver_id,
        driver_nationality,
        driver_url,
        driver_date_of_birth,
        driver_family_name,
        driver_given_name,
        driver_permanent_number,
        driver_code,
        driver_timestamp,
        constructor_id,
        constructor_url,
        constructor_nationality,
        constructor_name,
        constructor_timestamp,
        number,
        "$position",
        position_text,
        points,
        grid,
        laps,
        status,
        time,
        milliseconds,
        fastest_lap,
        rank,
        fastest_lap_time,
        fastest_lap_milliseconds,
        fastest_lap_speed,
        fastest_lap_speed_units,
        timestamp
    )
}

@JvmName("getConstructorSeasonRaceResultsListToRaceResultsArray")
fun List<GetConstructorSeasonRaceResults>.toRaceResultsArray(): Array<RaceWithResultsType> {
    return groupBy { "${it.races_round}" }.map { entry ->
        val race = entry.value[0]
        RaceWithResultsType(
            season = "${race.races_season}",
            round = "${race.races_round}",
            url = race.races_url,
            raceName = race.races_race_name,
            circuit = CircuitType(
                circuitId = race.circuit_id,
                url = race.circuit_url,
                circuitName = race.circuit_name,
                location = LocationType(
                    alt = race.circuit_alt,
                    lat = race.circuit_lat,
                    long = race.circuit_long,
                    locality = race.circuit_locality,
                    country = race.circuit_country
                )
            ),
            date = race.races_date,
            time = race.races_time,
            results = entry.value.map { it.toRaceResultsCachedData() }.toArrayResultType()
        )
    }.toTypedArray()
}

@JvmName("getDriverSeasonRaceResultsToRaceResultsCachedData")
fun GetDriverSeasonRaceResults.toRaceResultsCachedData(): RaceResultsCachedData {
    return RaceResultsCachedData(
        race_id,
        driver_id,
        driver_nationality,
        driver_url,
        driver_date_of_birth,
        driver_family_name,
        driver_given_name,
        driver_permanent_number,
        driver_code,
        driver_timestamp,
        constructor_id,
        constructor_url,
        constructor_nationality,
        constructor_name,
        constructor_timestamp,
        number,
        "$position",
        position_text,
        points,
        grid,
        laps,
        status,
        time,
        milliseconds,
        fastest_lap,
        rank,
        fastest_lap_time,
        fastest_lap_milliseconds,
        fastest_lap_speed,
        fastest_lap_speed_units,
        timestamp
    )
}

@JvmName("getDriverSeasonRaceResultsListToRaceResultsArray")
fun List<GetDriverSeasonRaceResults>.toRaceResultsArray(): Array<RaceWithResultsType> {
    return groupBy { "${it.races_round}" }.map { entry ->
        val race = entry.value[0]
        RaceWithResultsType(
            season = "${race.races_season}",
            round = "${race.races_round}",
            url = race.races_url,
            raceName = race.races_race_name,
            circuit = CircuitType(
                circuitId = race.circuit_id,
                url = race.circuit_url,
                circuitName = race.circuit_name,
                location = LocationType(
                    alt = race.circuit_alt,
                    lat = race.circuit_lat,
                    long = race.circuit_long,
                    locality = race.circuit_locality,
                    country = race.circuit_country
                )
            ),
            date = race.races_date,
            time = race.races_time,
            results = entry.value.map { it.toRaceResultsCachedData() }.toArrayResultType()
        )
    }.toTypedArray()
}

@JvmName("getLastRaceResultsToRaceResultsCachedData")
fun GetLastRaceResults.toRaceResultsCachedData(): RaceResultsCachedData {
    return RaceResultsCachedData(
        race_id,
        driver_id,
        driver_nationality,
        driver_url,
        driver_date_of_birth,
        driver_family_name,
        driver_given_name,
        driver_permanent_number,
        driver_code,
        driver_timestamp,
        constructor_id,
        constructor_url,
        constructor_nationality,
        constructor_name,
        constructor_timestamp,
        number,
        "$position",
        position_text,
        points,
        grid,
        laps,
        status,
        time,
        milliseconds,
        fastest_lap,
        rank,
        fastest_lap_time,
        fastest_lap_milliseconds,
        fastest_lap_speed,
        fastest_lap_speed_units,
        timestamp
    )
}

@JvmName("getRaceResultsWithRaceIdToRaceResultsCachedData")
fun GetRaceResultsWithRaceId.toRaceResultsCachedData(): RaceResultsCachedData {
    return RaceResultsCachedData(
        race_id,
        driver_id,
        driver_nationality,
        driver_url,
        driver_date_of_birth,
        driver_family_name,
        driver_given_name,
        driver_permanent_number,
        driver_code,
        driver_timestamp,
        constructor_id,
        constructor_url,
        constructor_nationality,
        constructor_name,
        constructor_timestamp,
        number,
        "$position",
        position_text,
        points,
        grid,
        laps,
        status,
        time,
        milliseconds,
        fastest_lap,
        rank,
        fastest_lap_time,
        fastest_lap_milliseconds,
        fastest_lap_speed,
        fastest_lap_speed_units,
        timestamp
    )
}

fun List<RaceResultsCachedData>.toArrayResultType(): Array<ResultType> {
    return map {
        ResultType(
            number = it.number,
            position = it.position,
            positionText = it.position_text,
            points = it.points,
            driver = DriverType(
                driverId = it.driver_id,
                permanentNumber = it.driver_permanent_number,
                code = it.driver_code,
                url = it.driver_url,
                givenName = it.driver_given_name,
                familyName = it.driver_family_name,
                dateOfBirth = it.driver_date_of_birth,
                nationality = it.driver_nationality
            ),
            constructor = ConstructorType(
                constructorId = it.constructor_id,
                url = it.constructor_url,
                name = it.constructor_name,
                nationality = it.constructor_nationality
            ),
            grid = it.grid,
            laps = it.laps,
            status = it.status,
            time = it.time?.let { _ ->
                DurationType(
                    time = it.time,
                    millis = it.milliseconds
                )
            },
            fastestLap = it.fastest_lap?.let { _ ->
                it.rank?.let { _ ->
                    it.fastest_lap_speed?.let { _ ->
                        FastestLapType(
                            rank = it.rank,
                            lap = it.fastest_lap,
                            time = it.fastest_lap_time?.let { _ ->
                                DurationType(
                                    time = it.fastest_lap_time,
                                    millis = it.fastest_lap_milliseconds
                                )
                            },
                            averageSpeed = AverageSpeedType(
                                speed = it.fastest_lap_speed,
                                units = it.fastest_lap_speed_units
                            )
                        )
                    }
                }
            }
        )
    }.toTypedArray()
}

fun Pair<RaceScheduleCachedData, List<RaceResultsCachedData>>.toRaceWithResultsType(): RaceWithResultsType {
    val (raceSchedule, raceResults) = this
    return RaceWithResultsType(
        season = "${raceSchedule.season}",
        round = "${raceSchedule.round}",
        url = raceSchedule.url,
        raceName = raceSchedule.race_name,
        circuit = CircuitType(
            circuitId = raceSchedule.circuit_id,
            url = raceSchedule.circuit_url,
            circuitName = raceSchedule.circuit_name,
            location = LocationType(
                alt = raceSchedule.circuit_alt,
                lat = raceSchedule.circuit_lat,
                long = raceSchedule.circuit_long,
                locality = raceSchedule.circuit_locality,
                country = raceSchedule.circuit_country
            )
        ),
        date = raceSchedule.date,
        time = raceSchedule.time,
        results = raceResults.toArrayResultType()
    )
}
