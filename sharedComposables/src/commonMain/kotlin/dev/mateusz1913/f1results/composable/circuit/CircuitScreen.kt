package dev.mateusz1913.f1results.composable.circuit

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mateusz1913.f1results.composable.common.Loading
import dev.mateusz1913.f1results.composable.common.MapboxMapView
import dev.mateusz1913.f1results.composable.di.getViewModelInstance
import dev.mateusz1913.f1results.viewmodel.CircuitViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CircuitScreen(
    circuitId: String,
    circuitViewModel: CircuitViewModel = getViewModelInstance { parametersOf(circuitId) }
) {
    val circuitState = circuitViewModel.circuitState.collectAsState()
    val circuit = circuitState.value.circuit
    if (circuitState.value.isFetching) {
        Loading()
        return
    }
    if (circuit == null) {
        Text("No circuit")
        return
    }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            val latitude = circuit.location.lat?.toDouble()
            val longitude = circuit.location.long?.toDouble()
            if (latitude != null && longitude != null) {
                Box(modifier = Modifier.weight(1f)) {
                    MapboxMapView(
                        modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 250.dp),
                        latitude = latitude,
                        longitude = longitude
                    )
                }
            }
            Text(circuit.circuitName, modifier = Modifier.padding(vertical = 10.dp))
        }
    }
}