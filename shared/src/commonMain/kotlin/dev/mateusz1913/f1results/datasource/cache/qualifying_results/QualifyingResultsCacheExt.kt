package dev.mateusz1913.f1results.datasource.cache.qualifying_results

import dev.mateusz1913.f1results.datasource.cache.GetLastQualifyingResults
import dev.mateusz1913.f1results.datasource.cache.GetQualifyingResultsWithRaceId
import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import dev.mateusz1913.f1results.datasource.data.qualifying_results.QualifyingResultType
import kotlin.jvm.JvmName

@JvmName("getLastQualifyingResultsToArrayQualifyingResultType")
fun List<GetLastQualifyingResults>.toArrayQualifyingResultType(): Array<QualifyingResultType> {
    return map {
        QualifyingResultType(
            number = it.number,
            position = it.position,
            driver = DriverType(
                driverId = it.driver_id,
                permanentNumber = it.driver_permanent_number,
                code = it.driver_code,
                url = it.driver_url,
                givenName = it.driver_given_name,
                familyName = it.driver_family_name,
                dateOfBirth = it.driver_date_of_birth,
                nationality = it.driver_nationality
            ),
            constructor = ConstructorType(
                constructorId = it.constructor_id,
                url = it.constructor_url,
                name = it.constructor_name,
                nationality = it.constructor_nationality
            ),
            Q1 = it.q1,
            Q2 = it.q2,
            Q3 = it.q3
        )
    }.toTypedArray()
}

@JvmName("getQualifyingResultsWithRaceIdToArrayQualifyingResultType")
fun List<GetQualifyingResultsWithRaceId>.toArrayQualifyingResultType(): Array<QualifyingResultType> {
    return map {
        QualifyingResultType(
            number = it.number,
            position = it.position,
            driver = DriverType(
                driverId = it.driver_id,
                permanentNumber = it.driver_permanent_number,
                code = it.driver_code,
                url = it.driver_url,
                givenName = it.driver_given_name,
                familyName = it.driver_family_name,
                dateOfBirth = it.driver_date_of_birth,
                nationality = it.driver_nationality
            ),
            constructor = ConstructorType(
                constructorId = it.constructor_id,
                url = it.constructor_url,
                name = it.constructor_name,
                nationality = it.constructor_nationality
            ),
            Q1 = it.q1,
            Q2 = it.q2,
            Q3 = it.q3
        )
    }.toTypedArray()
}
