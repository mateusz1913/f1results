package dev.mateusz1913.f1results.datasource.cache.requests_timestamps

fun getCircuitRequest(circuitId: String) = "circuit/$circuitId"

fun getConstructorRequest(constructorId: String) = "constructor/$constructorId"

fun getDriverRequest(driverId: String) = "driver/$driverId"

fun getQualifyingResultsRequest(season: String, round: String) = "qualifyingResults/$season/$round"

fun getRaceResultsRequest(season: String, round: String) = "raceResults/$season/$round"

fun getConstructorSeasonRaceResultsRequest(season: String, constructorId: String) =
    "constructorSeasonRaceResults/$season/$constructorId"

fun getDriverSeasonRaceResultsRequest(season: String, driverId: String) =
    "driverSeasonRaceResults/$season/$driverId"

fun getRaceScheduleListRequest(season: String) = "raceSchedule/$season"

fun getRaceScheduleRequest(season: String, round: String) = "raceSchedule/$season/$round"

fun getConstructorSeasonListRequest(constructorId: String) = "constructorSeasonList/$constructorId"

fun getDriverSeasonListRequest(driverId: String) = "driverSeasonList/$driverId"

fun getConstructorStandingsRequest(season: String, round: String) =
    "constructorStandings/$season/$round"

fun getConstructorSeasonStandingRequest(constructorId: String, season: String) =
    "constructorSeasonStanding/$constructorId/$season"

fun getDriverStandingsRequest(season: String, round: String) =
    "driverStandings/$season/$round"

fun getDriverSeasonStandingRequest(driverId: String, season: String) =
    "driverSeasonStanding/$driverId/$season"
