package dev.mateusz1913.f1results.desktop

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.sun.javafx.application.PlatformImpl
import dev.burnoo.cokoin.Koin
import dev.mateusz1913.f1results.composable.theme.F1ResultsTheme
import dev.mateusz1913.f1results.desktop.composable.LocalWindow
import dev.mateusz1913.f1results.desktop.presentation.navigation.Navigation
import dev.mateusz1913.f1results.di.initKoinAppDeclaration
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun main() = application {
    Napier.base(DebugAntilog())

    val finishListener = object : PlatformImpl.FinishListener {
        override fun idle(implicitExit: Boolean) {}
        override fun exitCalled() {}
    }
    PlatformImpl.addListener(finishListener)

    val windowState = rememberWindowState(width = 1000.dp, height = 1000.dp)
    Window(state = windowState, title = "F1Results", onCloseRequest = {
        PlatformImpl.removeListener(finishListener)
        exitApplication()
    }) {
        Koin(appDeclaration = {
            initKoinAppDeclaration(this)
        }) {
            F1ResultsTheme {
                CompositionLocalProvider(LocalWindow provides this.window) {
                    Navigation()
                }
            }
        }
    }
}
