package dev.mateusz1913.f1results.composable.circuit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.mateusz1913.f1results.composable.di.getViewModelInstance
import dev.mateusz1913.f1results.viewmodel.CircuitViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CircuitScreen(
    circuitId: String,
    circuitViewModel: CircuitViewModel = getViewModelInstance { parametersOf(circuitId) }
) {
    val circuitState = circuitViewModel.circuitState.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            val circuit = circuitState.value.circuit
            when {
                circuit != null -> {
                    Text(circuit.circuitName)
                }
                circuitState.value.isFetching -> {
                    CircularProgressIndicator(color = MaterialTheme.colors.secondary)
                }
                else -> {
                    Text("No circuit")
                }
            }
        }
    }
}