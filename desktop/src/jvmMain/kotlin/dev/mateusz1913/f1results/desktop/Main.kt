package dev.mateusz1913.f1results.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.burnoo.cokoin.Koin
import dev.mateusz1913.f1results.composable.theme.F1ResultsTheme
import dev.mateusz1913.f1results.desktop.presentation.navigation.Navigation
import dev.mateusz1913.f1results.di.initKoinAppDeclaration

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Koin(appDeclaration = {
            initKoinAppDeclaration(this)
        }) {
            F1ResultsTheme {
                Navigation()
            }
        }
    }
}
