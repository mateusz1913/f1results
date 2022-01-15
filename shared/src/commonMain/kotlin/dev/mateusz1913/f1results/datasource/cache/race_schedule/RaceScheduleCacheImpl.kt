package dev.mateusz1913.f1results.datasource.cache.race_schedule

import dev.mateusz1913.f1results.datasource.cache.CircuitQueries
import dev.mateusz1913.f1results.datasource.cache.GetRaceWithRaceId
import dev.mateusz1913.f1results.datasource.cache.GetRaceWithSeason
import dev.mateusz1913.f1results.datasource.cache.RaceQueries
import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceType
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds

class RaceScheduleCacheImpl(
    private val raceScheduleQueries: RaceQueries,
    private val circuitQueries: CircuitQueries
) : RaceScheduleCache {
    override fun getRaceScheduleWithSeason(season: String): List<GetRaceWithSeason> {
        return raceScheduleQueries.getRaceWithSeason(season).executeAsList()
    }

    override fun getRaceScheduleWithSeasonAndRound(season: String, round: String): GetRaceWithRaceId {
        return raceScheduleQueries.getRaceWithRaceId("$season/$round").executeAsOne()
    }

    override fun insertRaceSchedule(raceSchedule: RaceType) {
        raceScheduleQueries.transaction {
            raceScheduleQueries.insertRace(
                race_id = "${raceSchedule.season}/${raceSchedule.round}",
                season = raceSchedule.season,
                round = raceSchedule.round,
                url = raceSchedule.url,
                race_name = raceSchedule.raceName,
                circuit_id = raceSchedule.circuit.circuitId,
                date = raceSchedule.date,
                time = raceSchedule.time,
                timestamp = now().toEpochMilliseconds()
            )
            circuitQueries.insertCircuit(
                circuit_id = raceSchedule.circuit.circuitId,
                url = raceSchedule.circuit.url,
                circuit_name = raceSchedule.circuit.circuitName,
                country = raceSchedule.circuit.location.country,
                locality = raceSchedule.circuit.location.locality,
                alt = raceSchedule.circuit.location.alt,
                lat = raceSchedule.circuit.location.lat,
                long = raceSchedule.circuit.location.long,
                timestamp = now().toEpochMilliseconds()
            )
        }
    }

    override fun insertRaceScheduleList(raceScheduleList: Array<RaceType>) {
        raceScheduleList.forEach { raceSchedule ->
            insertRaceSchedule(raceSchedule)
        }
    }
}
