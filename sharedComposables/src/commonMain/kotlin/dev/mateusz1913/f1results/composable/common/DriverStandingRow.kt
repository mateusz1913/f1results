package dev.mateusz1913.f1results.composable.common

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
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StandingPosition(driverStanding.positionText)
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!noDriverInfo) {
                        Text(
                            "${driverStanding.driver.givenName} ${driverStanding.driver.familyName}",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val constructorsNames = driverStanding.constructors.joinToString(", ") { it.name }
                    Text(
                        constructorsNames,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(
                            start = if (!noDriverInfo) 10.dp else 0.dp,
                            top = 2.dp,
                            end = 10.dp,
                            bottom = 2.dp
                        )
                    )
                }
            }
        }
        StandingScore(driverStanding.points, driverStanding.wins)
    }
}
