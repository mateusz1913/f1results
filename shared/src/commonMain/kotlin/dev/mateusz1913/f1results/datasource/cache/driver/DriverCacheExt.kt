package dev.mateusz1913.f1results.datasource.cache.driver

import dev.mateusz1913.f1results.datasource.cache.Driver_Entity
import dev.mateusz1913.f1results.datasource.data.driver.DriverType

fun Driver_Entity.toDriverType(): DriverType {
    return DriverType(
        driverId = driver_id,
        permanentNumber = permanent_number,
        code = code,
        url = url,
        givenName = given_name,
        familyName = family_name,
        dateOfBirth = date_of_birth,
        nationality = nationality
    )
}
