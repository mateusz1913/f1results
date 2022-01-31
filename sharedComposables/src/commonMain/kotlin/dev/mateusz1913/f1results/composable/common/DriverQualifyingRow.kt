package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.composable.navigation.LocalNavController
import dev.mateusz1913.f1results.datasource.data.qualifying_results.QualifyingResultType

@Composable
fun DriverQualifyingRow(result: QualifyingResultType, noDriverInfo: Boolean = false) {
    val navigationController = LocalNavController.current
    Row(
        modifier = Modifier.fillMaxWidth().clickable(enabled = !noDriverInfo) {
            navigationController.navigateToDriverScreen(result.driver.driverId)
        },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f)) {
            StandingPosition(result.position)
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!noDriverInfo) {
                        Text(
                            "${result.driver.givenName} ${result.driver.familyName}",
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
                                val number = result.number
                                if (number != null) {
                                    Text(number, fontSize = 14.sp, fontWeight = FontWeight.Medium)
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
                        result.constructor.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                    )
                    Box(modifier = Modifier.padding(vertical = 2.dp)) {
                        Text(
                            result.Q3 ?: result.Q2 ?: result.Q1,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(end = 10.dp),
                            textAlign = TextAlign.Right
                        )
                    }
                }
            }
        }
    }
}
