package dev.mateusz1913.f1results.mocks

import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType

val race01 = RaceWithResultsType(
    season = "2021",
    round = "1",
    url = "",
    raceName = "Bahrain Grand Prix",
    circuit = circuit01,
    date = "05.03.2021",
    time = "18:00",
    results = arrayOf(result02, result01)
)

val race02 = RaceWithResultsType(
    season = "2021",
    round = "2",
    url = "",
    raceName = "Australian Grand Prix",
    circuit = circuit02,
    date = "19.03.2021",
    time = "18:00",
    results = arrayOf(result03, result04)
)
