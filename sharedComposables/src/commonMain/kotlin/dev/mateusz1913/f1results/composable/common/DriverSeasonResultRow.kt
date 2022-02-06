package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.composable.navigation.LocalNavController
import dev.mateusz1913.f1results.datasource.data.race_results.ResultType

@Composable
fun DriverSeasonResultRow(driverResult: ResultType, noDriverInfo: Boolean = false) {
    val navigationController = LocalNavController.current
    Column {
        val driverStartingPosition = when (driverResult.grid) {
            "0" -> "Pit lane"
            else -> driverResult.grid
        }
        InfoRow(
            label = "Started: ",
            value = driverStartingPosition,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Box(modifier = Modifier.clickable(enabled = !noDriverInfo) {
            navigationController.navigateToDriverScreen(driverResult.driver.driverId)
        }) {
            DriverResultRow(driverResult, noDriverInfo = noDriverInfo)
        }
    }
}
