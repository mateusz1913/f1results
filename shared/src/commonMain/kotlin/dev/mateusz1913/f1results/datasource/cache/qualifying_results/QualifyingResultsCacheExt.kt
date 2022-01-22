package dev.mateusz1913.f1results.datasource.cache.qualifying_results

import dev.mateusz1913.f1results.datasource.cache.GetLastQualifyingResults
import dev.mateusz1913.f1results.datasource.cache.GetQualifyingResultsWithRaceId
import dev.mateusz1913.f1results.datasource.cache.race_schedule.RaceScheduleCachedData
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.data.circuit.LocationType
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import dev.mateusz1913.f1results.datasource.data.qualifying_results.QualifyingResultType
import dev.mateusz1913.f1results.datasource.data.qualifying_results.RaceWithQualifyingResultsType
import kotlin.jvm.JvmName

data class QualifyingResultsCachedData(
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
    val q1: String,
    val q2: String?,
    val q3: String?,
    val timestamp: Double
)

@JvmName("getLastQualifyingResultsToQualifyingResultsCachedData")
fun GetLastQualifyingResults.toQualifyingResultsCachedData(): QualifyingResultsCachedData {
    return QualifyingResultsCachedData(
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
        q1,
        q2,
        q3,
        timestamp
    )
}

@JvmName("getQualifyingResultsWithRaceIdToQualifyingResultsCachedData")
fun GetQualifyingResultsWithRaceId.toQualifyingResultsCachedData(): QualifyingResultsCachedData {
    return QualifyingResultsCachedData(
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
        q1,
        q2,
        q3,
        timestamp
    )
}

fun List<QualifyingResultsCachedData>.toArrayQualifyingResultType(): Array<QualifyingResultType> {
    return map {
        QualifyingResultType(
            number = it.number,
            position = it.position,
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
            Q1 = it.q1,
            Q2 = it.q2,
            Q3 = it.q3
        )
    }.toTypedArray()
}

fun Pair<RaceScheduleCachedData, List<QualifyingResultsCachedData>>.toRaceWithQualifyingResultsType(): RaceWithQualifyingResultsType {
    val (raceSchedule, qualifyingResults) = this
    return RaceWithQualifyingResultsType(
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
        qualifyingResults = qualifyingResults.toArrayQualifyingResultType()
    )
}
