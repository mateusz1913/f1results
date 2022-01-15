package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.composable.theme.F1ResultsColor
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingType

@Composable
fun DriverStandingsRow(driverStanding: DriverStandingType, noDriverInfo: Boolean = false) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .clickable(enabled = !noDriverInfo) {
                // Handle navigation to driver screen
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Column(
                modifier = Modifier
                    .size(40.dp)
                    .border(0.dp, color = Color.Transparent, RoundedCornerShape(20.dp))
                    .background(
                        color = when (driverStanding.positionText) {
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
                    driverStanding.positionText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
        Column(modifier = Modifier
            .weight(1f)) {
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.padding(end = 4.dp)) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                            append("Wins: ")
                        }
                        append(driverStanding.wins)
                    },
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
                Column(
                    modifier = Modifier
                        .size(30.dp)
                        .border(0.dp, Color.Transparent, RoundedCornerShape(15.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(driverStanding.points, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
