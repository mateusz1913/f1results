package dev.mateusz1913.f1results.android.presentation.preview.common

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.mateusz1913.f1results.composable.common.ConstructorStandingRow
import dev.mateusz1913.f1results.composable.common.DriverStandingRow
import dev.mateusz1913.f1results.composable.common.StandingPosition
import dev.mateusz1913.f1results.composable.common.StandingScore
import dev.mateusz1913.f1results.mocks.constructorStanding01
import dev.mateusz1913.f1results.mocks.driverStanding01

@Preview(showBackground = true)
@Composable
fun ConstructorStandingRowPreview() {
    ConstructorStandingRow(constructorStanding = constructorStanding01)
}

@Preview(showBackground = true)
@Composable
fun DriverStandingRowPreview() {
    DriverStandingRow(driverStanding = driverStanding01)
}

@Preview(showBackground = true)
@Composable
fun StandingPositionPreview() {
    Column {
        StandingPosition(position = "1")
        StandingPosition(position = "2")
        StandingPosition(position = "3")
        StandingPosition(position = "13")
    }
}

@Preview(showBackground = true)
@Composable
fun StandingScorePreview() {
    StandingScore(points = "123", wins = "1")
}
