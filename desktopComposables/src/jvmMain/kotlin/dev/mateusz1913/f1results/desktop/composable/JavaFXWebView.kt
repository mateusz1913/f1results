package dev.mateusz1913.f1results.desktop.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.web.WebView
import javafx.scene.paint.Color as JFXColor

@Composable
fun JavaFXWebView(modifier: Modifier = Modifier, webViewContent: String) {
    JavaFXPanel(
        modifier = modifier,
        factory = { JFXPanel() },
        update = {
            Platform.runLater {
                val webView = WebView()
                webView.engine.loadContent(webViewContent)
                val stackPane = StackPane()
                stackPane.children.add(webView)
                stackPane.alignment = Pos.CENTER
                StackPane.setAlignment(webView, Pos.CENTER)
                val scene = Scene(stackPane, JFXColor.GRAY)
                it.scene = scene
            }
        }
    )
}
