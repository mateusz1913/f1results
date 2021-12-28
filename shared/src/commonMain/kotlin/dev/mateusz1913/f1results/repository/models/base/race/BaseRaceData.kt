package dev.mateusz1913.f1results.repository.models.base.race

import dev.mateusz1913.f1results.repository.models.base.BaseData

interface BaseRaceData<T: BaseRaceType>: BaseData {
    val raceTable: BaseRaceTableType<T>
}