package dev.mateusz1913.f1results.datasource.data.base.race

import dev.mateusz1913.f1results.datasource.data.base.BaseData

interface BaseRaceData<T: BaseRaceType>: BaseData {
    val raceTable: BaseRaceTableType<T>
}