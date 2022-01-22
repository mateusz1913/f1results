package dev.mateusz1913.f1results.datasource.cache.driver

import dev.mateusz1913.f1results.datasource.cache.Driver_Entity
import dev.mateusz1913.f1results.datasource.data.driver.DriverType

interface DriverCache {
    fun get(driverId: String): Driver_Entity
    fun insert(driver: DriverType): Boolean
    fun insert(drivers: Array<DriverType>): Boolean
}
