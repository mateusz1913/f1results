package dev.mateusz1913.f1results.repository.models.base.standings

interface BaseStandingsTableType<T: BaseStandingsType> {
    val standingsLists: Array<T>
}