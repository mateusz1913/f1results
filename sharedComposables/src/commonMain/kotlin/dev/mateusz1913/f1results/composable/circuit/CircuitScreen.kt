package dev.mateusz1913.f1results.composable.circuit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.mateusz1913.f1results.composable.common.Loading
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
    CircuitLayoutWithMap(circuit) {
        Box(
            modifier = Modifier.height(128.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(circuit.circuitName, modifier = Modifier.padding(vertical = 10.dp))
            }
        }
    }
}