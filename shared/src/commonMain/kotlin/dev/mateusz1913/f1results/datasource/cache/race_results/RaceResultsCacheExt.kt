package dev.mateusz1913.f1results.datasource.cache.race_results

import dev.mateusz1913.f1results.datasource.cache.GetLastRaceResults
import dev.mateusz1913.f1results.datasource.cache.GetRaceResultsWithRaceId
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import dev.mateusz1913.f1results.datasource.data.race_results.AverageSpeedType
import dev.mateusz1913.f1results.datasource.data.race_results.DurationType
import dev.mateusz1913.f1results.datasource.data.race_results.FastestLapType
import dev.mateusz1913.f1results.datasource.data.race_results.ResultType
import kotlin.jvm.JvmName

@JvmName("getLastRaceResultsToArrayResultType")
fun List<GetLastRaceResults>.toArrayResultType(): Array<ResultType> {
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

@JvmName("getRaceResultsWithRaceIdToArrayResultType")
fun List<GetRaceResultsWithRaceId>.toArrayResultType(): Array<ResultType> {
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
