package dev.mateusz1913.f1results.datasource.cache.qualifying_results

import dev.mateusz1913.f1results.datasource.cache.*
import dev.mateusz1913.f1results.datasource.cache.race_schedule.RaceScheduleCachedData
import dev.mateusz1913.f1results.datasource.cache.race_schedule.toRaceScheduleCachedData
import dev.mateusz1913.f1results.datasource.data.qualifying_results.RaceWithQualifyingResultsType
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class QualifyingResultsCacheImpl(
    private val qualifyingResultsQueries: QualifyingResultsQueries,
    private val raceScheduleQueries: RaceQueries,
    private val circuitQueries: CircuitQueries,
    private val driverQueries: DriverQueries,
    private val constructorQueries: ConstructorQueries
) : QualifyingResultsCache {
    override fun getLatestQualifyingResults(): Pair<RaceScheduleCachedData, List<QualifyingResultsCachedData>> {
        val raceSchedule =
            raceScheduleQueries.getLatestRace().executeAsOne().toRaceScheduleCachedData()
        val qualifyingResults = qualifyingResultsQueries.getLastQualifyingResults().executeAsList()
            .map { it.toQualifyingResultsCachedData() }
        return Pair(raceSchedule, qualifyingResults)
    }

    override fun getQualifyingResultsWithSeasonAndRound(
        season: String,
        round: String
    ): Pair<RaceScheduleCachedData, List<QualifyingResultsCachedData>> {
        val raceSchedule = raceScheduleQueries.getRaceWithRaceId("$season/$round").executeAsOne()
            .toRaceScheduleCachedData()
        val qualifyingResults =
            qualifyingResultsQueries.getQualifyingResultsWithRaceId("$season/$round")
                .executeAsList().map { it.toQualifyingResultsCachedData() }
        return Pair(raceSchedule, qualifyingResults)
    }

    override fun insertQualifyingResults(qualifyingResults: RaceWithQualifyingResultsType): Boolean {
        return try {
            qualifyingResultsQueries.transactionWithResult {
                raceScheduleQueries.insertRace(
                    race_id = "${qualifyingResults.season}/${qualifyingResults.round}",
                    season = qualifyingResults.season.toLong(),
                    round = qualifyingResults.round.toLong(),
                    url = qualifyingResults.url,
                    race_name = qualifyingResults.raceName,
                    circuit_id = qualifyingResults.circuit.circuitId,
                    date = qualifyingResults.date,
                    time = qualifyingResults.time,
                    timestamp = now().toEpochMilliseconds()
                )
                circuitQueries.insertCircuit(
                    circuit_id = qualifyingResults.circuit.circuitId,
                    url = qualifyingResults.circuit.url,
                    circuit_name = qualifyingResults.circuit.circuitName,
                    country = qualifyingResults.circuit.location.country,
                    locality = qualifyingResults.circuit.location.locality,
                    alt = qualifyingResults.circuit.location.alt,
                    lat = qualifyingResults.circuit.location.lat,
                    long = qualifyingResults.circuit.location.long,
                    timestamp = now().toEpochMilliseconds()
                )
                qualifyingResults.qualifyingResults.forEach { result ->
                    qualifyingResultsQueries.insertQualifyingResults(
                        id = "${qualifyingResults.season}/${qualifyingResults.round}/${result.driver.driverId}",
                        race_id = "${qualifyingResults.season}/${qualifyingResults.round}",
                        driver_id = result.driver.driverId,
                        constructor_id = result.constructor.constructorId,
                        number = result.number,
                        position = result.position.toLong(),
                        q1 = result.Q1,
                        q2 = result.Q2,
                        q3 = result.Q3,
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
            Napier.w("insertQualifyingResults - ${e.message}", e, "QualifyingResultsCache")
            false
        }
    }
}