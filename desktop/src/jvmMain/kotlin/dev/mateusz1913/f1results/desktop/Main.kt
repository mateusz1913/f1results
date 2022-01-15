package dev.mateusz1913.f1results.desktop

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.burnoo.cokoin.Koin
import dev.mateusz1913.f1results.di.initKoinAppDeclaration
import dev.mateusz1913.f1results.composable.circuit.CircuitScreen
import dev.mateusz1913.f1results.composable.current_race_results.CurrentRaceResultsScreen
import dev.mateusz1913.f1results.composable.current_standings.CurrentStandingsScreen
import dev.mateusz1913.f1results.composable.driver.DriverScreen
import dev.mateusz1913.f1results.composable.theme.F1ResultsTheme
import dev.mateusz1913.f1results.desktop.presentation.navigation.Navigation

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Koin(appDeclaration = {
            initKoinAppDeclaration(this)
        }) {
            F1ResultsTheme {
                Scaffold(
                    topBar = { TopAppBar(title = { Text("F1Results") }) }
                ) {
//                CircuitScreen("monza")
//                CurrentRaceResultsScreen()
//                    CurrentStandingsScreen()
//                    DriverScreen("alonso")
                    Navigation()
                }
            }
        }
    }
}
