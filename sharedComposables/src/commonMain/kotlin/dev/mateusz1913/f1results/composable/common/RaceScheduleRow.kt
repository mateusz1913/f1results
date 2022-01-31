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
import dev.mateusz1913.f1results.composable.navigation.LocalNavController
import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceType

@Composable
fun RaceScheduleRow(race: RaceType) {
    val navigationController = LocalNavController.current
    Row(
        modifier = Modifier.padding(horizontal = 24.dp).clickable {
            navigationController.navigateToCircuitScreen(race.circuit.circuitId)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.padding(start = 2.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)) {
            Column(
                modifier = Modifier.size(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(race.round, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(2f)) {
                Text(
                    race.raceName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                Column(
                    modifier = Modifier.padding(
                        start = 4.dp,
                        end = 10.dp,
                        top = 2.dp,
                        bottom = 2.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(race.date, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
