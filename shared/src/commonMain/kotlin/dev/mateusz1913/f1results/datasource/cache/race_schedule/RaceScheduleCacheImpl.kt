package dev.mateusz1913.f1results.datasource.cache.race_schedule

import dev.mateusz1913.f1results.datasource.cache.*
import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceType
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class RaceScheduleCacheImpl(
    private val raceScheduleQueries: RaceQueries,
    private val circuitQueries: CircuitQueries
) : RaceScheduleCache {
    override fun getLastRaceSchedule(): RaceScheduleCachedData {
        return raceScheduleQueries.getLatestRace().executeAsOne().toRaceScheduleCachedData()
    }

    override fun getRaceScheduleFromCurrentSeason(): List<RaceScheduleCachedData> {
        return raceScheduleQueries.getRacesFromCurrentSeason().executeAsList()
            .map { it.toRaceScheduleCachedData() }
    }

    override fun getRaceScheduleWithSeason(season: String): List<RaceScheduleCachedData> {
        return raceScheduleQueries.getRaceWithSeason(season.toLong()).executeAsList()
            .map { it.toRaceScheduleCachedData() }
    }

    override fun getRaceScheduleWithSeasonAndRound(
        season: String,
        round: String
    ): RaceScheduleCachedData {
        return raceScheduleQueries.getRaceWithRaceId("$season/$round").executeAsOne()
            .toRaceScheduleCachedData()
    }

    override fun insertRaceSchedule(raceSchedule: RaceType): Boolean {
        return try {
            raceScheduleQueries.transactionWithResult {
                raceScheduleQueries.insertRace(
                    race_id = "${raceSchedule.season}/${raceSchedule.round}",
                    season = raceSchedule.season.toLong(),
                    round = raceSchedule.round.toLong(),
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
                true
            }
        } catch (e: Exception) {
            Napier.w("insertRaceSchedule - ${e.message}", e, "RaceScheduleCache")
            false
        }
    }

    override fun insertRaceScheduleList(raceScheduleList: Array<RaceType>): Boolean {
        return try {
            raceScheduleQueries.transactionWithResult {
                raceScheduleList.map { raceSchedule ->
                    raceScheduleQueries.insertRace(
                        race_id = "${raceSchedule.season}/${raceSchedule.round}",
                        season = raceSchedule.season.toLong(),
                        round = raceSchedule.round.toLong(),
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
                    true
                }.reduce { acc, curr ->
                    acc && curr
                }
            }
        } catch (e: Exception) {
            Napier.w("insertRaceScheduleList - ${e.message}", e, "RaceScheduleCache")
            false
        }
    }
}
