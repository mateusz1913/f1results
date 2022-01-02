package dev.mateusz1913.f1results.datasource.data.base.race

interface BaseRaceTableType<T: BaseRaceType> {
    val races: Array<T>
}