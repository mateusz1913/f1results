package dev.mateusz1913.f1results.desktop.composable

import androidx.compose.runtime.compositionLocalOf
import java.awt.Window

val LocalWindow = compositionLocalOf<Window> { error("CompositionLocal LocalWindow not provided") }
