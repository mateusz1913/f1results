package dev.mateusz1913.f1results.mocks

import dev.mateusz1913.f1results.datasource.data.race_results.*

val result01 = ResultType(
    number = "3",
    position = "6",
    positionText = "6",
    points = "8",
    driver = driver01,
    constructor = constructor01,
    grid = "7",
    laps = "65",
    status = "",
    time = DurationType(
        millis = null,
        time = "+12.0"
    ),
    fastestLap = FastestLapType(
        rank = "3",
        lap = "4",
        time = DurationType(
            millis = "123",
            time = "1:23",
        ),
        averageSpeed = AverageSpeedType(
            speed = "260",
            units = "kmh"
        )
    )
)

val result02 = ResultType(
    number = "4",
    position = "3",
    positionText = "3",
    points = "15",
    driver = driver02,
    constructor = constructor01,
    grid = "5",
    laps = "65",
    status = "",
    time = DurationType(
        millis = null,
        time = "+4.0"
    ),
    fastestLap = FastestLapType(
        rank = "2",
        lap = "24",
        time = DurationType(
            millis = "123",
            time = "1:22",
        ),
        averageSpeed = AverageSpeedType(
            speed = "265",
            units = "kmh"
        )
    )
)

val result03 = ResultType(
    number = "3",
    position = "8",
    positionText = "8",
    points = "4",
    driver = driver01,
    constructor = constructor01,
    grid = "9",
    laps = "65",
    status = "",
    time = DurationType(
        millis = "135",
        time = "+45.135"
    ),
    fastestLap = FastestLapType(
        rank = "3",
        lap = "4",
        time = DurationType(
            millis = "123",
            time = "1:23",
        ),
        averageSpeed = AverageSpeedType(
            speed = "260",
            units = "kmh"
        )
    )
)

val result04 = ResultType(
    number = "4",
    position = "13",
    positionText = "13",
    points = "0",
    driver = driver02,
    constructor = constructor01,
    grid = "11",
    laps = "65",
    status = "",
    time = DurationType(
        millis = null,
        time = "+54.0"
    ),
    fastestLap = FastestLapType(
        rank = "12",
        lap = "24",
        time = DurationType(
            millis = "123",
            time = "1:24",
        ),
        averageSpeed = AverageSpeedType(
            speed = "255",
            units = "kmh"
        )
    )
)
