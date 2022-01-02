package dev.mateusz1913.f1results.datasource.data.base.standings

interface BaseStandingsTableType<T: BaseStandingsType> {
    val standingsLists: Array<T>
}