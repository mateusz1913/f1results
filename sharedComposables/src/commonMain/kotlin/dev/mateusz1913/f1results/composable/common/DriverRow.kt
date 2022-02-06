package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.datasource.data.qualifying_results.QualifyingResultType
import dev.mateusz1913.f1results.datasource.data.race_results.FastestLapType
import dev.mateusz1913.f1results.datasource.data.race_results.ResultType

@Composable
fun DriverQualifyingRow(result: QualifyingResultType, noDriverInfo: Boolean = false) {
    DriverRow(
        position = result.position,
        driverName = "${result.driver.givenName} ${result.driver.familyName}",
        driverNumber = result.number,
        driverConstructorName = result.constructor.name,
        driverTime = result.Q3 ?: result.Q2 ?: result.Q1,
        noDriverPoints = true,
        noDriverInfo = noDriverInfo
    )
}

@Composable
fun DriverResultRow(result: ResultType, noDriverInfo: Boolean = false) {
    DriverRow(
        position = result.positionText,
        driverName = "${result.driver.givenName} ${result.driver.familyName}",
        driverNumber = result.number,
        driverConstructorName = result.constructor.name,
        driverTime = result.time?.time ?: result.status,
        noDriverPoints = false,
        driverPoints = result.points,
        driverFastestLap = result.fastestLap,
        noDriverInfo = noDriverInfo
    )
}

@Composable
fun DriverRow(
    position: String,
    driverName: String,
    driverNumber: String? = null,
    driverConstructorName: String,
    driverTime: String,
    noDriverPoints: Boolean = false,
    driverPoints: String? = null,
    driverFastestLap: FastestLapType? = null,
    noDriverInfo: Boolean = false
) {
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
            StandingPosition(position)
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!noDriverInfo) {
                        Text(
                            driverName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                        )
                        Box(modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)) {
                            Column(
                                modifier = Modifier
                                    .border(
                                        1.dp,
                                        MaterialTheme.colors.onBackground,
                                        RoundedCornerShape(13.dp)
                                    )
                                    .size(26.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                if (driverNumber != null) {
                                    Text(
                                        driverNumber,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        driverConstructorName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                    )
                    Box(modifier = Modifier.padding(vertical = 2.dp)) {
                        Text(
                            driverTime,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(end = 10.dp),
                            textAlign = TextAlign.Right
                        )
                    }
                }
            }
        }
        if (!noDriverPoints) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.LocalFireDepartment,
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary.copy(alpha = if (driverFastestLap?.rank == "1") 1f else 0f)
                )
                Column(
                    modifier = Modifier.size(30.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .padding(start = 2.dp, end = 10.dp, top = 2.dp, bottom = 2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(driverPoints ?: "-", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
