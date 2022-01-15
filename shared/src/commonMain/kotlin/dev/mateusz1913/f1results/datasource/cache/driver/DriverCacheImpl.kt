package dev.mateusz1913.f1results.datasource.cache.driver

import dev.mateusz1913.f1results.datasource.cache.DriverQueries
import dev.mateusz1913.f1results.datasource.cache.Driver_Entity
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds

class DriverCacheImpl(private val queries: DriverQueries): DriverCache {
    override fun get(driverId: String): Driver_Entity {
        return queries.getDriverById(driverId).executeAsOne()
    }

    override fun insert(driver: DriverType) {
        queries.insertDriver(
            driver_id = driver.driverId,
            permanent_number = driver.permanentNumber,
            code = driver.code,
            url = driver.url,
            given_name = driver.givenName,
            family_name = driver.familyName,
            date_of_birth = driver.dateOfBirth,
            nationality = driver.nationality,
            timestamp = now().toEpochMilliseconds()
        )
    }

    override fun insert(drivers: Array<DriverType>) {
        drivers.forEach { driver ->
            insert(driver)
        }
    }
}
