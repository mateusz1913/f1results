package dev.mateusz1913.f1results.android.presentation.preview.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.mateusz1913.f1results.composable.common.DriverSeasonResultRow
import dev.mateusz1913.f1results.mocks.result01

@Preview(showBackground = true, widthDp = 400, heightDp = 150)
@Composable
fun DriverSeasonResultRowPreview() {
    DriverSeasonResultRow(driverResult = result01)
}

@Preview(showBackground = true, widthDp = 400, heightDp = 150)
@Composable
fun DriverSeasonResultRowNoDriverInfoPreview() {
    DriverSeasonResultRow(driverResult = result01, noDriverInfo = true)
}
