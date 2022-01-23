package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.mateusz1913.f1results.desktop.composable.JavaFXWebView

@Composable
actual fun MapboxMapView(modifier: Modifier, latitude: Double, longitude: Double) {
    val backgroundColor = MaterialTheme.colors.background
    JavaFXWebView(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
            .background(color = backgroundColor),
        webViewContent = prepareStaticMapImageHTML(
            latitude,
            longitude,
            "rgba(${backgroundColor.red * 255}, ${backgroundColor.green * 255}, ${backgroundColor.blue * 255}, ${backgroundColor.alpha})"
        )
    )
}

fun prepareStaticMapImageHTML(latitude: Double, longitude: Double, color: String): String {
    val imgSrc =
        "https://api.mapbox.com/styles/v1/mapbox/satellite-v9/static/pin-s+FF8C00($longitude,$latitude)/$longitude,$latitude,13.5,0.00,0.00/500x500?access_token=pk.eyJ1IjoibWF0ZXVzejE5MTMiLCJhIjoiY2t5bTBic2Y5M2JsbjJ4cGJqaHAzbTh1eiJ9.NddT1twGmoTRaX9NA9bypg"
    return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8">
            <style>
                html,
                body {
                    background-color: $color;
                    height: 100%;
                    margin: 0;
                    padding: 0;
                }
                #mapboxMapImgContainer {
                    display: flex;
                    align-items: center;
                    background-color: transparent;
                    height: 100%;
                    justify-content: center;
                    width: 100%;
                }
            </style>
        </head>
        <body>
            <div id='mapboxMapImgContainer'>
                <img alt='static Mapbox map of the San Francisco bay area' src='$imgSrc' >
            <div>
        </body>
        </html>
    """.trimIndent()
}
