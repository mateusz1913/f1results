package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingType

@Composable
fun DriverStandingRow(driverStanding: DriverStandingType, noDriverInfo: Boolean = false) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .clickable(enabled = !noDriverInfo) {
                // Handle navigation to driver screen
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        StandingPosition(driverStanding.positionText)
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            if (!noDriverInfo) {
                Text(
                    "${driverStanding.driver.givenName} ${driverStanding.driver.familyName}",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            val constructorsNames = driverStanding.constructors.joinToString(", ") { it.name }
            Text(
                constructorsNames,
                modifier = Modifier.padding(
                    start = if (!noDriverInfo) 10.dp else 0.dp,
                    top = 2.dp,
                    end = 10.dp,
                    bottom = 2.dp
                )
            )
        }
        StandingScore(driverStanding.points, driverStanding.wins)
    }
}
