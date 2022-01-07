package dev.mateusz1913.f1results.android.presentation.current_calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.mateusz1913.f1results.android.utils.NetworkMonitor

@Composable
fun CurrentCalendarScreen(navController: NavController) {
    val networkConnectionState by NetworkMonitor.shared.state.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("Current calendar ${if (networkConnectionState.isReachable) "Connected" else "Not connected" }")
            Button(onClick = {
                navController.navigate("circuitScreen/monza")
            }) {
                Text("Test circuit screen - Monza")
            }
            Button(onClick = {
                navController.navigate("circuitScreen/albert_park")
            }) {
                Text("Test circuit screen - Albert Park")
            }
        }
    }
}