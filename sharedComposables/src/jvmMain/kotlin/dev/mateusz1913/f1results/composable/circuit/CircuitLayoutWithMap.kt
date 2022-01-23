package dev.mateusz1913.f1results.composable.circuit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mateusz1913.f1results.composable.common.Loading
import dev.mateusz1913.f1results.composable.common.MapboxMapView
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
actual fun CircuitLayoutWithMap(circuit: CircuitType, content: @Composable () -> Unit) {
    val isMapReadyToBeDisplayed = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colors.background),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            val latitude = circuit.location.lat?.toDouble()
            val longitude = circuit.location.long?.toDouble()
            if (latitude != null && longitude != null) {
                Box(modifier = Modifier.size(500.dp)) {
                    /**
                     * To fix:
                     * Exception in thread "AWT-EventQueue-0 @coroutine#9" java.lang.IllegalArgumentException: Failed requirement.
                     * at androidx.compose.ui.node.MeasureAndLayoutDelegate.measureAndLayout
                     * ...
                     *
                     * let's delay composition of MapboxMapView
                     */
                    if (isMapReadyToBeDisplayed.value) {
                        MapboxMapView(
                            latitude = latitude,
                            longitude = longitude
                        )
                    } else {
                        Loading()
                    }
                }
            }
            Box(modifier = Modifier.heightIn(min = 128.dp)) {
                content()
            }
            LaunchedEffect(Unit) {
                launch {
                    delay(500)
                    isMapReadyToBeDisplayed.value = true
                }
            }
        }
    }
}
