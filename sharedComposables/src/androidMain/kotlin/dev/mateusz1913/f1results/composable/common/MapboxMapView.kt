package dev.mateusz1913.f1results.composable.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

@Composable
actual fun MapboxMapView(modifier: Modifier, latitude: Double, longitude: Double) {
    AndroidView(modifier = modifier, factory = { ctx ->
        MapView(ctx).apply {
            getMapboxMap().apply {
                loadStyleUri(Style.SATELLITE)
                setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(longitude, latitude))
                        .zoom(14.0)
                        .build()
                )
            }
        }
    })
}
