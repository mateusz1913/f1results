package dev.mateusz1913.f1results.composable.circuit

import androidx.compose.runtime.Composable
import dev.mateusz1913.f1results.datasource.data.circuit.CircuitType

/**
 * On Android displays map with circuit details in bottom sheet,
 * on JVM displays circuit details under the map
 */
@Composable
expect fun CircuitLayoutWithMap(circuit: CircuitType, content: @Composable () -> Unit)
