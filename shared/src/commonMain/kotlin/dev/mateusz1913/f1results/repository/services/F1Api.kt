package dev.mateusz1913.f1results.repository.services

import dev.mateusz1913.f1results.createKtorClient
import io.ktor.client.*

class F1Api(
    client: HttpClient = createKtorClient(),
    baseUrl: String = "https://ergast.com/api/f1"
) {
    val circuitsApi = CircuitsApi(client, baseUrl)
    val constructorsApi = ConstructorsApi(client, baseUrl)
    val driversApi = DriversApi(client, baseUrl)
    val finishingStatusApi = FinishingStatusApi(client, baseUrl)
    val lapTimesApi = LapTimesApi(client, baseUrl)
    val pitStopsApi = PitStopsApi(client, baseUrl)
    val qualifyingResultsApi = QualifyingResultsApi(client, baseUrl)
    val raceResultsApi = RaceResultsApi(client, baseUrl)
    val raceScheduleApi = RaceScheduleApi(client, baseUrl)
    val seasonListApi = SeasonListApi(client, baseUrl)
    val standingsApi = StandingsApi(client, baseUrl)
}