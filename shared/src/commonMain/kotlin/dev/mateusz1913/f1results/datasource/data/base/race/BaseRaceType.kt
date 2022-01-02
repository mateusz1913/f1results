package dev.mateusz1913.f1results.datasource.data.base.race

import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType

interface BaseRaceType {
    val season: String
    val round: String
    val url: String
    val raceName: String
    val circuit: CircuitType
    val date: String
    val time: String
}