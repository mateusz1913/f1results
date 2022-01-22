package dev.mateusz1913.f1results.datasource.cache.driver

import dev.mateusz1913.f1results.datasource.cache.DriverQueries
import dev.mateusz1913.f1results.datasource.cache.Driver_Entity
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import dev.mateusz1913.f1results.domain.now
import dev.mateusz1913.f1results.domain.toEpochMilliseconds
import io.github.aakira.napier.Napier

class DriverCacheImpl(private val queries: DriverQueries): DriverCache {
    override fun get(driverId: String): Driver_Entity {
        return queries.getDriverById(driverId).executeAsOne()
    }

    override fun insert(driver: DriverType): Boolean {
        return try {
            queries.transactionWithResult {
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
                true
            }
        } catch (e: Exception) {
            Napier.w("insertDriver - ${e.message}", e, "DriverCache")
            false
        }
    }

    override fun insert(drivers: Array<DriverType>): Boolean {
        return try {
            queries.transactionWithResult {
                drivers.map { driver ->
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
                    true
                }.reduce { acc, curr ->
                    acc && curr
                }
            }
        } catch (e: Exception) {
            Napier.w("insertDrivers - ${e.message}", e, "DriverCache")
            false
        }
    }
}
