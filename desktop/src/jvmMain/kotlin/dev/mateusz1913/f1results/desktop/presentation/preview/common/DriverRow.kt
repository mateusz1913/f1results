package dev.mateusz1913.f1results.desktop.presentation.preview.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import dev.mateusz1913.f1results.composable.common.DriverQualifyingRow
import dev.mateusz1913.f1results.composable.common.DriverResultRow
import dev.mateusz1913.f1results.mocks.qualifyingResult01
import dev.mateusz1913.f1results.mocks.result01

@Preview
@Composable
fun DriverQualifyingResultRowPreview() {
    DriverQualifyingRow(result = qualifyingResult01)
}

@Preview
@Composable
fun DriverQualifyingResultRowNoDriverInfoPreview() {
    DriverQualifyingRow(result = qualifyingResult01, noDriverInfo = true)
}

@Preview
@Composable
fun DriverResultRowPreview() {
    DriverResultRow(result = result01)
}

@Preview
@Composable
fun DriverResultRowNoDriverInfoPreview() {
    DriverResultRow(result = result01, noDriverInfo = true)
}
