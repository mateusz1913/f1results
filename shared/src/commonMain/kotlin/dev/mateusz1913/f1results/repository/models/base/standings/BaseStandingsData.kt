package dev.mateusz1913.f1results.repository.models.base.standings

import dev.mateusz1913.f1results.repository.models.base.BaseData

interface BaseStandingsData<T: BaseStandingsType>: BaseData {
    val standingsTable: BaseStandingsTableType<T>
}