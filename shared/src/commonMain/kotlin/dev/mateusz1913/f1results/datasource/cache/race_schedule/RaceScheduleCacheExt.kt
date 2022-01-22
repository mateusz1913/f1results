package dev.mateusz1913.f1results.datasource.cache.race_schedule

import dev.mateusz1913.f1results.datasource.cache.*
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.data.circuit.LocationType
import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceType
import kotlin.jvm.JvmName

data class RaceScheduleCachedData(
    val race_id: String,
    val season: Long,
    val round: Long,
    val url: String,
    val race_name: String,
    val date: String,
    val time: String,
    val timestamp: Double,
    val circuit_id: String,
    val circuit_url: String,
    val circuit_name: String,
    val circuit_country: String,
    val circuit_locality: String,
    val circuit_alt: String?,
    val circuit_lat: String?,
    val circuit_long: String?,
    val circuit_timestamp: Double
)

@JvmName("getLatestRaceToRaceScheduleCachedData")
fun GetLatestRace.toRaceScheduleCachedData(): RaceScheduleCachedData {
    return RaceScheduleCachedData(
        race_id,
        season,
        round,
        url,
        race_name,
        date,
        time,
        timestamp,
        circuit_id,
        circuit_url,
        circuit_name,
        circuit_country,
        circuit_locality,
        circuit_alt,
        circuit_lat,
        circuit_long,
        circuit_timestamp
    )
}

@JvmName("getRacesFromCurrentSeasonToRaceScheduleCachedData")
fun GetRacesFromCurrentSeason.toRaceScheduleCachedData(): RaceScheduleCachedData {
    return RaceScheduleCachedData(
        race_id,
        season,
        round,
        url,
        race_name,
        date,
        time,
        timestamp,
        circuit_id,
        circuit_url,
        circuit_name,
        circuit_country,
        circuit_locality,
        circuit_alt,
        circuit_lat,
        circuit_long,
        circuit_timestamp
    )
}

@JvmName("getRaceWithRaceIdToRaceScheduleCachedData")
fun GetRaceWithRaceId.toRaceScheduleCachedData(): RaceScheduleCachedData {
    return RaceScheduleCachedData(
        race_id,
        season,
        round,
        url,
        race_name,
        date,
        time,
        timestamp,
        circuit_id,
        circuit_url,
        circuit_name,
        circuit_country,
        circuit_locality,
        circuit_alt,
        circuit_lat,
        circuit_long,
        circuit_timestamp
    )
}

@JvmName("getRaceWithSeasonToRaceScheduleCachedData")
fun GetRaceWithSeason.toRaceScheduleCachedData(): RaceScheduleCachedData {
    return RaceScheduleCachedData(
        race_id,
        season,
        round,
        url,
        race_name,
        date,
        time,
        timestamp,
        circuit_id,
        circuit_url,
        circuit_name,
        circuit_country,
        circuit_locality,
        circuit_alt,
        circuit_lat,
        circuit_long,
        circuit_timestamp
    )
}

fun RaceScheduleCachedData.toRaceType(): RaceType {
    return RaceType(
        season = "$season",
        round = "$round",
        url = url,
        raceName = race_name,
        circuit = CircuitType(
            circuitId = circuit_id,
            url = circuit_url,
            circuitName = circuit_name,
            location = LocationType(
                country = circuit_country,
                alt = circuit_alt,
                lat = circuit_lat,
                long = circuit_long,
                locality = circuit_locality
            )
        ),
        date = date,
        time = time
    )
}

fun List<RaceScheduleCachedData>.toArrayRaceType(): Array<RaceType> {
    return map {
        RaceType(
            season = "${it.season}",
            round = "${it.round}",
            url = it.url,
            raceName = it.race_name,
            circuit = CircuitType(
                circuitId = it.circuit_id,
                url = it.circuit_url,
                circuitName = it.circuit_name,
                location = LocationType(
                    country = it.circuit_country,
                    alt = it.circuit_alt,
                    lat = it.circuit_lat,
                    long = it.circuit_long,
                    locality = it.circuit_locality
                )
            ),
            date = it.date,
            time = it.time
        )
    }.toTypedArray()
}
