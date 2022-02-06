package dev.mateusz1913.f1results.desktop.presentation.preview.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import dev.mateusz1913.f1results.composable.common.ConstructorStandingRow
import dev.mateusz1913.f1results.composable.common.DriverStandingRow
import dev.mateusz1913.f1results.composable.common.StandingPosition
import dev.mateusz1913.f1results.composable.common.StandingScore
import dev.mateusz1913.f1results.mocks.constructorStanding01
import dev.mateusz1913.f1results.mocks.driverStanding01

@Preview
@Composable
fun ConstructorStandingRowPreview() {
    ConstructorStandingRow(constructorStanding = constructorStanding01)
}

@Preview
@Composable
fun DriverStandingRowPreview() {
    DriverStandingRow(driverStanding = driverStanding01)
}

@Preview
@Composable
fun StandingPositionPreview() {
    Column {
        StandingPosition(position = "1")
        StandingPosition(position = "2")
        StandingPosition(position = "3")
        StandingPosition(position = "13")
    }
}

@Preview
@Composable
fun StandingScorePreview() {
    StandingScore(points = "123", wins = "1")
}
