package dev.mateusz1913.f1results.repository.models.base.race

interface BaseRaceTableType<T: BaseRaceType> {
    val races: Array<T>
}