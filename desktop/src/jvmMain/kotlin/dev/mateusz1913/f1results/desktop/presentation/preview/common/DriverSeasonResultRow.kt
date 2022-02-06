package dev.mateusz1913.f1results.desktop.presentation.preview.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import dev.mateusz1913.f1results.composable.common.DriverSeasonResultRow
import dev.mateusz1913.f1results.mocks.result01

@Preview
@Composable
fun DriverSeasonResultRowPreview() {
    DriverSeasonResultRow(driverResult = result01)
}

@Preview
@Composable
fun DriverSeasonResultRowNoDriverInfoPreview() {
    DriverSeasonResultRow(driverResult = result01, noDriverInfo = true)
}
