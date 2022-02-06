package dev.mateusz1913.f1results.android.presentation.preview.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.mateusz1913.f1results.composable.common.DriverQualifyingRow
import dev.mateusz1913.f1results.composable.common.DriverResultRow
import dev.mateusz1913.f1results.mocks.qualifyingResult01
import dev.mateusz1913.f1results.mocks.result01

@Preview(showBackground = true, widthDp = 400, heightDp = 100)
@Composable
fun DriverQualifyingResultRowPreview() {
    DriverQualifyingRow(result = qualifyingResult01)
}

@Preview(showBackground = true, widthDp = 400, heightDp = 100)
@Composable
fun DriverQualifyingResultRowNoDriverInfoPreview() {
    DriverQualifyingRow(result = qualifyingResult01, noDriverInfo = true)
}

@Preview(showBackground = true, widthDp = 400, heightDp = 100)
@Composable
fun DriverResultRowPreview() {
    DriverResultRow(result = result01)
}

@Preview(showBackground = true, widthDp = 400, heightDp = 100)
@Composable
fun DriverResultRowNoDriverInfoPreview() {
    DriverResultRow(result = result01, noDriverInfo = true)
}
