package dev.mateusz1913.f1results.android.presentation.circuit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.burnoo.cokoin.viewmodel.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CircuitScreen(
    circuitId: String,
    circuitViewModel: CircuitViewModel = getViewModel(parameters = { parametersOf(circuitId) })
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            val circuit = circuitViewModel.circuit
            when {
                circuit != null -> {
                    Text(circuit.circuitName)
                }
                circuitViewModel.isFetchingCircuit -> {
                    CircularProgressIndicator(color = MaterialTheme.colors.secondary)
                }
                else -> {
                    Text("No circuit")
                }
            }
        }
    }
}