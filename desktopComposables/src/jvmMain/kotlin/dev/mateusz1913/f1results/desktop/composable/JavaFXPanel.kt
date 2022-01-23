package dev.mateusz1913.f1results.desktop.composable

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateObserver
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.round
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Container
import java.util.concurrent.atomic.AtomicBoolean
import javax.swing.JPanel

/**
 * Similar to SwingPanel:
 * https://github.com/JetBrains/androidx/blob/jb-main/compose/ui/ui/src/desktopMain/kotlin/androidx/compose/ui/awt/SwingPanel.desktop.kt
 */
@Composable
fun <T : Component> JavaFXPanel(
    background: Color = MaterialTheme.colors.background,
    factory: () -> T,
    modifier: Modifier = Modifier,
    update: (T) -> Unit = {}
) {
    val componentInfo = remember {
        ComponentInfo<T>().apply {
            layout = JPanel()
        }
    }

    val container = LocalWindow.current
    val density = LocalDensity.current.density

    Layout(
        content = {},
        modifier = modifier.onGloballyPositioned { childCoordinates ->
            val coordinates = childCoordinates.parentCoordinates!!
            val location = coordinates.localToWindow(Offset.Zero).round()
            val size = coordinates.size
            componentInfo.layout.setBounds(
                (location.x / density).toInt(),
                (location.y / density).toInt(),
                (size.width / density).toInt(),
                (size.height / density).toInt()
            )
            componentInfo.layout.validate()
            componentInfo.layout.repaint()
        },
        measurePolicy = { _, _ ->
            layout(0, 0) {}
        }
    )

    DisposableEffect(factory) {
        componentInfo.factory = factory()
        componentInfo.layout.apply {
            layout = BorderLayout(0, 0)
            add(componentInfo.factory)
        }
        componentInfo.updater = Updater(componentInfo.factory, update)
        container.add(componentInfo.layout)
        onDispose {
            container.remove(componentInfo.layout)
            componentInfo.updater.dispose()
        }
    }

    SideEffect {
        componentInfo.layout.background = parseColor(background)
        componentInfo.updater.update = update
    }
}

private fun parseColor(color: Color): java.awt.Color {
    return java.awt.Color(
        color.component1(),
        color.component2(),
        color.component3(),
        color.component4()
    )
}

private class ComponentInfo<T : Component> {
    lateinit var layout: Container
    lateinit var factory: T
    lateinit var updater: Updater<T>
}

private class Updater<T : Component>(
    private val component: T,
    update: (T) -> Unit
) {
    private var isDisposed = false
    private val isUpdateScheduled = AtomicBoolean()
    private val snapshotObserver = SnapshotStateObserver { command ->
        command()
    }

    private val scheduleUpdate = { _: T ->
        if (!isUpdateScheduled.getAndSet(true)) {
            Platform.runLater {
                isUpdateScheduled.set(false)
                if (!isDisposed) {
                    performUpdate()
                }
            }
        }
    }

    var update: (T) -> Unit = update
        set(value) {
            if (field != value) {
                field = value
                performUpdate()
            }
        }

    private fun performUpdate() {
        // don't replace scheduleUpdate by lambda reference,
        // scheduleUpdate should always be the same instance
        snapshotObserver.observeReads(component, scheduleUpdate) {
            update(component)
        }
    }

    init {
        snapshotObserver.start()
        performUpdate()
    }

    fun dispose() {
        snapshotObserver.stop()
        snapshotObserver.clear()
        isDisposed = true
    }
}
