package dev.mateusz1913.f1results.datasource.cache.race_schedule

import dev.mateusz1913.f1results.datasource.cache.GetRaceWithRaceId
import dev.mateusz1913.f1results.datasource.cache.GetRaceWithSeason
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.data.circuit.LocationType
import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceType

fun GetRaceWithRaceId.toRaceType(): RaceType {
    return RaceType(
        season = season,
        round = round,
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

fun List<GetRaceWithSeason>.toArrayRaceType(): Array<RaceType> {
    return map {
        RaceType(
            season = it.season,
            round = it.round,
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
