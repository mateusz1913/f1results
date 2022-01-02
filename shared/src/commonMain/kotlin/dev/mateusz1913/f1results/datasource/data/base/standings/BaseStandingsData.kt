package dev.mateusz1913.f1results.datasource.data.base.standings

import dev.mateusz1913.f1results.datasource.data.base.BaseData

interface BaseStandingsData<T: BaseStandingsType>: BaseData {
    val standingsTable: BaseStandingsTableType<T>
}