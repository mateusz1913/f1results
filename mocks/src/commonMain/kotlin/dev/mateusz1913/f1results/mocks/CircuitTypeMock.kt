package dev.mateusz1913.f1results.mocks

import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import dev.mateusz1913.f1results.datasource.data.circuit.LocationType

val circuit01 = CircuitType(
    circuitId = "bahrain",
    url = "",
    circuitName = "Sakhir",
    location = LocationType(
        alt = null,
        lat = null,
        long = null,
        locality = "Sakhir",
        country = "Bahrain"
    )
)

val circuit02 = CircuitType(
    circuitId = "melbourne",
    url = "",
    circuitName = "Albert Park",
    location = LocationType(
        alt = null,
        lat = null,
        long = null,
        locality = "Melbourne",
        country = "Australia"
    )
)
