package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.composable.theme.F1ResultsColor
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingType
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingType

@Composable
fun ConstructorStandingRow(constructorStanding: ConstructorStandingType) {
    BaseStandingRow(
        position = constructorStanding.position,
        leftText = constructorStanding.constructor.name,
        rightText = null,
        points = constructorStanding.points,
        wins = constructorStanding.wins,
    )
}

@Composable
fun DriverStandingRow(driverStanding: DriverStandingType, noDriverInfo: Boolean = false) {
    BaseStandingRow(
        position = driverStanding.positionText,
        leftText = "${driverStanding.driver.givenName} ${driverStanding.driver.familyName}",
        rightText = driverStanding.constructors.joinToString(", ") { it.name },
        points = driverStanding.points,
        wins = driverStanding.wins,
        noDriverInfo = noDriverInfo
    )
}

@Composable
fun BaseStandingRow(
    position: String,
    leftText: String,
    rightText: String?,
    points: String,
    wins: String,
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
                            leftText,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                if (rightText != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            rightText,
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
        }
        StandingScore(points, wins)
    }
}

@Composable
fun StandingPosition(position: String) {
    Column(modifier = Modifier.padding(10.dp)) {
        Column(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    color = when (position) {
                        "1" -> F1ResultsColor.FirstPlace
                        "2" -> F1ResultsColor.SecondPlace
                        "3" -> F1ResultsColor.ThirdPlace
                        else -> Color.Transparent
                    }
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                position,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
fun StandingScore(points: String, wins: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.padding(end = 4.dp)) {
            InfoRow(
                label = "Wins: ",
                value = wins,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Column(
            modifier = Modifier.padding(
                start = 4.dp,
                top = 2.dp,
                end = 10.dp,
                bottom = 2.dp
            )
        ) {
            Text(points, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}
